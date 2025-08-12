package com.peachgb.userService.users.controller;

import com.peachgb.userService.routes.APIRoutes;
import com.peachgb.userService.users.dtos.UserDTO;
import com.peachgb.userService.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIRoutes.USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUserData(){
        return ResponseEntity.ok(userService.getUserData("email"));
    }

}
