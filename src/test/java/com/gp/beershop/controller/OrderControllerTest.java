package com.gp.beershop.controller;


import com.gp.beershop.dto.*;
import com.gp.beershop.mock.OrderMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends AbstractControllerTest {

    @BeforeEach
    public void defaultSystem() {
        OrderMock.defaultState();
    }

    @Test
    public void testAddOrder() throws Exception {
        mockMvc.perform(post("/api/admin/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        mapper.writeValueAsString(OrderRequest.builder()
                                .customerId(2)
                                .goods(List.of(
                                        Goods.builder()
                                                .id(2)
                                                .count(1)
                                                .build(),
                                        Goods.builder()
                                                .id(3)
                                                .count(3)
                                                .build()
                                ))
                                .build()
                        )))
                .andExpect(status().isCreated())
                .andExpect(content().json(
                        mapper.writeValueAsString(
                                Orders.builder()
                                        .id(2)
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
                                                                .inStock(true)
                                                                .name("Аливария")
                                                                .description("Пиво номер 1 в Беларуси")
                                                                .alcohol(4.6)
                                                                .density(10.2)
                                                                .country("Республика Беларусь")
                                                                .price(3D)
                                                                .build())
                                                        .count(1)
                                                        .build(),
                                                Order.builder()
                                                        .beer(Beer.builder()
                                                                .id(3)
                                                                .type("светлое осветлённое")
                                                                .inStock(true)
                                                                .name("Pilsner Urquell")
                                                                .description("непастеризованное")
                                                                .alcohol(4.2)
                                                                .density(12.0)
                                                                .country("Чехия")
                                                                .price(8D)
                                                                .build())
                                                        .count(3)
                                                        .build()
                                        ))
                                        .build()
                        )));
    }

    @Test
    public void testUpdateOrderById() throws Exception {
        mockMvc.perform(patch("/api/admin/orders/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"processed\": true\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(
                                IdResponse.builder()
                                        .id(2)
                                        .build()
                        )
                ));
    }

    @Test
    public void testShowAllOrders() throws Exception {
        mockMvc.perform(get("/api/admin/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(
                                List.of(Orders.builder()
                                                .id(1)
                                                .customer(Customer.builder()
                                                        .id(1)
                                                        .name("Иван Иванов")
                                                        .email("ivan.ivanov@mail.ru")
                                                        .phone("+375331234567")
                                                        .build())
                                                .processed(true)
                                                .total(25D)
                                                .order(List.of(
                                                        Order.builder()
                                                                .beer(Beer.builder()
                                                                        .id(1)
                                                                        .type("светлое")
                                                                        .inStock(true)
                                                                        .name("Лидское")
                                                                        .description("Лучшее пиво по бабушкиным рецептам")
                                                                        .alcohol(5.0)
                                                                        .density(11.5)
                                                                        .country("Республика Беларусь")
                                                                        .price(5D)
                                                                        .build())
                                                                .count(2)
                                                                .build(),
                                                        Order.builder()
                                                                .beer(Beer.builder()
                                                                        .id(2)
                                                                        .type("темное")
                                                                        .inStock(true)
                                                                        .name("Аливария")
                                                                        .description("Пиво номер 1 в Беларуси")
                                                                        .alcohol(4.6)
                                                                        .density(10.2)
                                                                        .country("Республика Беларусь")
                                                                        .price(3D)
                                                                        .build())
                                                                .count(5)
                                                                .build()
                                                )).build(),
                                        Orders.builder()
                                                .id(2)
                                                .customer(Customer.builder()
                                                        .id(2)
                                                        .name("Петр Петров")
                                                        .email("petr.petrov@yandex.ru")
                                                        .phone("+375337654321")
                                                        .build())
                                                .processed(true)
                                                .total(27D)
                                                .order(List.of(
                                                        Order.builder()
                                                                .beer(Beer.builder()
                                                                        .id(2)
                                                                        .type("темное")
                                                                        .inStock(true)
                                                                        .name("Аливария")
                                                                        .description("Пиво номер 1 в Беларуси")
                                                                        .alcohol(4.6)
                                                                        .density(10.2)
                                                                        .country("Республика Беларусь")
                                                                        .price(3D)
                                                                        .build())
                                                                .count(1)
                                                                .build(),
                                                        Order.builder()
                                                                .beer(Beer.builder()
                                                                        .id(3)
                                                                        .type("светлое осветлённое")
                                                                        .inStock(true)
                                                                        .name("Pilsner Urquell")
                                                                        .description("непастеризованное")
                                                                        .alcohol(4.2)
                                                                        .density(12.0)
                                                                        .country("Чехия")
                                                                        .price(8D)
                                                                        .build())
                                                                .count(3)
                                                                .build()
                                                ))
                                                .build()
                                )
                        )));
    }
}
