package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerOrder {
    private Beer beer;
    private Integer count;
}
