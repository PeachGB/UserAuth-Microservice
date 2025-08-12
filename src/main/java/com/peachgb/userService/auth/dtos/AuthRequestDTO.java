package com.peachgb.userService.auth.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDTO {
    private String email;
    private String password;

    public Boolean isEmail(){
        return email != null;
    }
}
