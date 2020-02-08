package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private Beer beer;
    private Integer volume;
}
