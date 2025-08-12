package com.peachgb.userService.auth.service;

import com.peachgb.userService.auth.dtos.AuthRequestDTO;
import com.peachgb.userService.auth.dtos.AuthResponseDTO;
import com.peachgb.userService.auth.utils.JWTUtil;
import com.peachgb.userService.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import com.peachgb.userService.users.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO login_user(AuthRequestDTO dto) {
        if (!userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("User not found");
        }
        if (!dto.isEmail()){
            throw new IllegalArgumentException("Email is obligatory");
        }
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow();
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authInputToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponseDTO.builder().token(token).build();
    }

    public AuthResponseDTO register(AuthRequestDTO registerDTO) {
        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = User.builder()
                .email(registerDTO.getEmail())
                .password_hash(encodedPassword).build();
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponseDTO.builder().token(token).build();
    }
}
