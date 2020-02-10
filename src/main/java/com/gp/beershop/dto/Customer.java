package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

@Data
@Builder
public class Customer {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String phone;
}
