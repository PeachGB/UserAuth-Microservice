package com.peachgb.userService.auth.controller;

import com.peachgb.userService.auth.dtos.AuthResponseDTO;
import com.peachgb.userService.auth.dtos.LoginDTO;
import com.peachgb.userService.auth.dtos.RegisterDTO;
import com.peachgb.userService.auth.service.AuthService;
import com.peachgb.userService.routes.APIRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIRoutes.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(LoginDTO dto){
        return ResponseEntity.ok(authService.login_user(dto));
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(RegisterDTO dto){
        return ResponseEntity.ok(authService.register(dto));
    }

}
