package com.gp.beershop.mock;

import com.gp.beershop.dto.Goods;
import com.gp.beershop.dto.OrderRequest;

import java.util.HashSet;
import java.util.Set;

public class OrderRequestMock {
    private OrderRequestMock() {
    }
    public static OrderRequest getOrderRequestWithEmptyOrder() {
        return OrderRequest.builder()
            .customerId(2)
            .goods(new HashSet<>())
            .build();
    }
    public static OrderRequest getOrderRequestByIvan() {
        return OrderRequest.builder()
            .customerId(1)
            .goods(Set.of(
                Goods.builder()
                    .id(2)
                    .amount(1)
                    .build(),
                Goods.builder()
                    .id(3)
                    .amount(3)
                    .build()))
            .build();
    }
    public static OrderRequest getOrderRequestByPetr() {
        return OrderRequest.builder()
            .customerId(2)
            .goods(Set.of(
                Goods.builder()
                    .id(2)
                    .amount(1)
                    .build(),
                Goods.builder()
                    .id(3)
                    .amount(3)
                    .build()))
            .build();
    }
    public static OrderRequest getOrderRequestByAdmin() {
        return OrderRequest.builder()
            .customerId(3)
            .goods(Set.of(
                Goods.builder()
                    .id(2)
                    .amount(1)
                    .build(),
                Goods.builder()
                    .id(3)
                    .amount(3)
                    .build()))
            .build();
    }
}
