package com.gp.beershop.controller;


import com.gp.beershop.dto.*;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.mapper.OrderMapper;
import com.gp.beershop.mock.BeerMock;
import com.gp.beershop.mock.CustomersMock;
import com.gp.beershop.mock.OrderMock;
import com.gp.beershop.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends AbstractControllerTest {
    @MockBean
    protected OrderRepository orderRepository;

    @SpyBean
    private OrderMapper orderMapper;

    @BeforeEach
    public void defaultSystem() {
        BeerMock.defaultState();
        OrderMock.defaultState();
    }

    @Test
    public void testAddOrder() throws Exception {
        final String token = signInAsCustomer();

        mockMvc.perform(post("/api/orders").header("Authorization", token)
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
                                        .customerOrders(List.of(
                                                CustomerOrder.builder()
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
                                                CustomerOrder.builder()
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
        final String token = signInAsCustomer();
        mockMvc.perform(patch("/api/orders/2").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"processed\": true\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(2)
                ));
    }

    @Test
    public void testShowAllOrders() throws Exception {
        final String token = signInAsCustomer();
        willReturn(OrderMock.getAllValues().stream()
                .map(orderMapper::sourceToDestination)
                .collect(Collectors.toList()))
                .given(orderRepository).findAll();
        mockMvc.perform(get("/api/orders").header("Authorization", token)
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
                                                .customerOrders(List.of(
                                                        CustomerOrder.builder()
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
                                                        CustomerOrder.builder()
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
                                                .customerOrders(List.of(
                                                        CustomerOrder.builder()
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
                                                        CustomerOrder.builder()
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
