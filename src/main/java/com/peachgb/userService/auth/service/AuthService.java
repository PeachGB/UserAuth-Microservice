package com.peachgb.userService.auth.service;

import com.peachgb.userService.auth.dtos.AuthResponseDTO;
import com.peachgb.userService.auth.dtos.LoginDTO;
import com.peachgb.userService.auth.dtos.RegisterDTO;
import com.peachgb.userService.auth.utils.JWTUtil;
import com.peachgb.userService.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import com.peachgb.userService.users.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO login_user(LoginDTO dto) {
        User user = null;
        if(dto.isUsername()) {user = userRepository.findByUsername(dto.getUsername()).orElseThrow();}
        if(dto.isEmail()) {user = userRepository.findByEmail(dto.getEmail()).orElseThrow();}
        if(user == null){throw new IllegalArgumentException("Invalid username or email");}
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities());
        authenticationManager.authenticate(authInputToken);
        String token = jwtUtil.generateToken(user.getName());
        return AuthResponseDTO.builder().token(token).build();
    }

    public AuthResponseDTO register(RegisterDTO registerDTO) {
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
        User user = User.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password_hash(encodedPassword).build();
         String token = jwtUtil.generateToken(user.getName());
         return AuthResponseDTO.builder().token(token).build();
    }
}
