package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSignUpRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
}
