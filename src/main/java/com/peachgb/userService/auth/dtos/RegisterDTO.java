package com.peachgb.userService.auth.dtos;

import lombok.Data;

@Data
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
}
