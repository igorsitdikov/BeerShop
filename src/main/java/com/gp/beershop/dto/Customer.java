package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private Integer id;
    private String name;
    private String email;
    private String phone;
}
