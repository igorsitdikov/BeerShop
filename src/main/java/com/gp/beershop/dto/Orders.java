package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Orders {
    private Integer id;
    private Customer customer;
    private Boolean processed;
    private Double total;
    private List<Order> order;
}
