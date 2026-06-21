package com.gp.beershop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Token of authorized user")
public class UserSignInResponse {
    @Schema(description = "JSON Web Token")
    private String token;
}
