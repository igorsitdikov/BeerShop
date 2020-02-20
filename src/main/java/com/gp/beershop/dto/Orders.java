package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Orders {
    private Integer id;
    private Customer customer;
    private Boolean processed;
    private Double total;
    private List<CustomerOrder> customerOrders;
}
