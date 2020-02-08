package com.gp.beershop.dto;

import lombok.Data;

@Data
public class CustomerSignUpRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
}
