package com.gp.beershop.mock;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.Goods;
import com.gp.beershop.dto.OrderRequest;

import java.util.Set;

public class OrderRequestMock {
    private OrderRequestMock() {
    }

    public static OrderRequest getOrderRequest() {
        return OrderRequest.builder()
            .customerId(2)
            .goods(Set.of(
                Goods.builder()
                    .id(2)
                    .count(1)
                    .build(),
                Goods.builder()
                    .id(3)
                    .count(3)
                    .build()))
            .build();
    }
}
