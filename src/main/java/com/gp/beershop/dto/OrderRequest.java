package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequest {
    private Integer customerId;
    private List<Goods> goods;
}
