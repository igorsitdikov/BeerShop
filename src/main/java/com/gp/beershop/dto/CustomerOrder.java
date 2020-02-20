package com.gp.beershop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerOrder {
    private Beer beer;
    private Integer count;
}
