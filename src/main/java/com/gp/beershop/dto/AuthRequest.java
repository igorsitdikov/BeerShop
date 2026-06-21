package com.gp.beershop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "All details about the authentication request.")
public class AuthRequest {
    @Schema(description = "User's email")
    private String email;

    @Schema(description = "User's password")
    private String password;
}
