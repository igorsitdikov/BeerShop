package com.gp.beershop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@ApiModel(description = "All details about the user")
@EqualsAndHashCode
public class UserDTO {
    @ApiModelProperty(notes = "The database generated order ID")
    private Integer id;
    @ApiModelProperty(notes = "User's name")
    @NotNull(message = "Should input user name")
    private String name;
    @ApiModelProperty(notes = "User's password")
    @EqualsAndHashCode.Exclude
    @NotNull(message = "Should input user password")
    private String password;
    @Email
    @NotNull(message = "Should input user email")
    @ApiModelProperty(notes = "User's email")
    private String email;
    @NotNull(message = "Should input user phone")
    @Pattern(regexp = "\\+375[0-9]{9}", message = "Phone number should started from +375, after that - 9 numbers")
    @ApiModelProperty(notes = "User's phone")
    private String phone;
}
