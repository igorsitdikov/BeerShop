package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String phone;
}
