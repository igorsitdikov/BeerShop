package com.gp.beershop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@ApiModel(description = "All details about the authentication request.")
public class AuthRequest {
    @ApiModelProperty(notes = "User's email")
    private String email;

    @ApiModelProperty(notes = "User's password")
    private String password;
}
