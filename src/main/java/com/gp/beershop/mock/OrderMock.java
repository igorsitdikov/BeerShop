package com.gp.beershop.mock;

import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.dto.UserDTO;

import java.math.BigDecimal;
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
            .total(BigDecimal.valueOf(25))
            .canceled(false)
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(1))
                        .amount(2)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(2))
                        .amount(5)
                        .build()
                       ))
            .build());
        put(2, Orders.builder()
            .id(2)
            .userDTO(UsersMock.getById(2))
            .processed(false)
            .canceled(false)
            .total(BigDecimal.valueOf(27))
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(2))
                        .amount(1)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(3))
                        .amount(3)
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
                .canceled(order.getCanceled())
                .total(order.getTotal())
                .customerOrders(order.getCustomerOrders())
                .build()).collect(Collectors.toList());
    }
}
