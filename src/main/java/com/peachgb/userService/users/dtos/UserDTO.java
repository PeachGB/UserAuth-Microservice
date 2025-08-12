package com.peachgb.userService.users.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String name;
    private String email;
}
