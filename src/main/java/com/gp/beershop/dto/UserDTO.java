package com.gp.beershop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(description = "All details about the user")
public class UserDTO {
    @ApiModelProperty(notes = "The database generated order ID")
    private Integer id;
    @ApiModelProperty(notes = "User's name")
    private String name;
    @ApiModelProperty(notes = "User's password")
    private String password;
    @ApiModelProperty(notes = "User's email")
    private String email;
    @ApiModelProperty(notes = "User's phone")
    private String phone;
}
