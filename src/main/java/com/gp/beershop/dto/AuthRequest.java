package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class AuthRequest {
    private String email;
    private String password;
}
