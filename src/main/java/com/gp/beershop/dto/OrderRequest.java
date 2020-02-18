package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class OrderRequest {
    private Integer customerId;
    private Set<Goods> goods;
}
