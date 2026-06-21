package com.gp.beershop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@Schema(description = "All details about the user")
@EqualsAndHashCode
public class User {
    @Schema(description = "The database generated order ID")
    private Long id;

    @Schema(description = "User's first name")
    @NotNull(message = "Should input user first name")
    private String firstName;

    @Schema(description = "User's second name")
    @NotNull(message = "Should input user second name")
    private String secondName;

    @Schema(description = "User's password")
    @EqualsAndHashCode.Exclude
    @NotNull(message = "Should input user password")
    private String password;

    @Email
    @NotNull(message = "Should input user email")
    @Schema(description = "User's email")
    private String email;

    @NotNull(message = "Should input user phone")
    @Pattern(regexp = "\\+375[0-9]{9}", message = "Phone number should started from +375, after that - 9 numbers")
    @Schema(description = "User's phone")
    private String phone;
}
