package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class OrderRequest {
    private Integer customerId;
    private Set<Goods> goods;
}
