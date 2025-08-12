package com.peachgb.userService.auth.dtos;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String email;
    private String password;

    public Boolean isUsername(){
        return username != null;
    }
    public Boolean isEmail(){
        return email != null;
    }
}
