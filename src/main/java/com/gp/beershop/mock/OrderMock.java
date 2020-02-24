package com.gp.beershop.mock;

import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.dto.UserDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class OrderMock {

    private OrderMock() {
    }

    private static Map<Integer, Orders> ordersMap = new HashMap<>() {{
        put(1, Orders.builder()
            .id(1)
            .userDTO(UsersMock.getById(1))
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
            .userDTO(UsersMock.getById(2))
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
        return ordersMap.values()
            .stream()
            .map(order -> Orders.builder()
                .id(order.getId())
                .userDTO(
                    UserDTO.builder()
                        .id(order.getUserDTO().getId())
                        .name(order.getUserDTO().getName())
                        .email(order.getUserDTO().getEmail())
                        .phone(order.getUserDTO().getPhone())
                        .build())
                .processed(order.getProcessed())
                .total(order.getTotal())
                .customerOrders(order.getCustomerOrders())
                .build()).collect(Collectors.toList());
    }
}
