package com.gp.beershop.mock;

import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.dto.Orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class OrderMock {

    private OrderMock() {
    }

    private static Map<Integer, Orders> ordersMap = new HashMap<>() {{
        put(1, Orders.builder()
            .id(1)
            .customer(CustomersMock.getById(1))
            .processed(true)
            .total(25D)
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(1))
                        .count(2)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(2))
                        .count(5)
                        .build()
                       ))
            .build());
        put(2, Orders.builder()
            .id(2)
            .customer(CustomersMock.getById(2))
            .processed(false)
            .total(27D)
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(2))
                        .count(1)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(3))
                        .count(3)
                        .build()
                       ))
            .build());
    }};

    public static Orders getById(final Integer id) {
        return ordersMap.get(id);
    }

    public static List<Orders> getAllValues() {
        return new ArrayList<>(ordersMap.values());
    }
}
