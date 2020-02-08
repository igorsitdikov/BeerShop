package com.gp.beershop.service;

import com.gp.beershop.dto.*;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Log
@Service
public class OrderService {

    private final List<Orders> orders = List.of(Orders.builder()
                    .id(1)
                    .customer(Customer.builder()
                            .id(1)
                            .name("Иван Иванов")
                            .email("ivan.ivanov@mail.ru")
                            .phone("+375331234567")
                            .build())
                    .processed(true)
                    .total(31D)
                    .order(List.of(
                            Order.builder()
                                    .beer(Beer.builder()
                                            .id(2)
                                            .type("темное")
                                            .in_stock(true)
                                            .name("Аливария")
                                            .description("Пиво номер 1 в Беларуси")
                                            .alcohol(4.6)
                                            .density(10.2)
                                            .country("Республика Беларусь")
                                            .price(3D)
                                            .build())
                                    .volume(5)
                                    .build(),
                            Order.builder().beer(Beer.builder()
                                    .id(3)
                                    .type("светлое осветлённое")
                                    .in_stock(true)
                                    .name("Pilsner Urquell")
                                    .description("непастеризованное")
                                    .alcohol(4.2)
                                    .density(12.0)
                                    .country("Чехия")
                                    .price(8D)
                                    .build())
                                    .volume(2)
                                    .build()
                    ))
                    .build(),
            Orders.builder().id(2)
                    .customer(Customer.builder()
                            .id(2)
                            .name("Петр Петров")
                            .email("petr.petrov@yandex.ru")
                            .phone("+375337654321")
                            .build())
                    .processed(false)
                    .total(27D)
                    .order(List.of(
                            Order.builder()
                                    .beer(Beer.builder()
                                            .id(2)
                                            .type("темное")
                                            .in_stock(true)
                                            .name("Аливария")
                                            .description("Пиво номер 1 в Беларуси")
                                            .alcohol(4.6)
                                            .density(10.2)
                                            .country("Республика Беларусь")
                                            .price(3D)
                                            .build())
                                    .volume(1)
                                    .build(),
                            Order.builder().beer(Beer.builder()
                                    .id(3)
                                    .type("светлое осветлённое")
                                    .in_stock(true)
                                    .name("Pilsner Urquell")
                                    .description("непастеризованное")
                                    .alcohol(4.2)
                                    .density(12.0)
                                    .country("Чехия")
                                    .price(8D)
                                    .build())
                                    .volume(3)
                                    .build()
                    ))
                    .build()
    );

    public Orders addOrder(OrderRequest request) {
        log.info("customer Id = " + request.getCustomerId());
        request.getGoods()
                .forEach(el -> log.info("goods id = " + el.getId() + " goods value = " + el.getValue()));
        return orders.get(1);
    }
    public IdResponse updateOrder(Integer id, OrderStatus request) {
        log.info("processed = " + request.getProcessed());
        return new IdResponse(id);
    }
    public List<Orders> showAllOrders() {
        return orders;
    }

}
