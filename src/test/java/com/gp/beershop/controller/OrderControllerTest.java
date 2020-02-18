package com.gp.beershop.controller;


import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.Customer;
import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.dto.Goods;
import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.entity.OrderEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.mapper.OrderMapper;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.mock.BeerMock;
import com.gp.beershop.mock.CustomersMock;
import com.gp.beershop.mock.OrderMock;
import com.gp.beershop.repository.BeerRepository;
import com.gp.beershop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends AbstractControllerTest {
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private BeerRepository beerRepository;
    @SpyBean
    private UserMapper userMapper;
    @SpyBean
    private BeerMapper beerMapper;
    @SpyBean
    private OrderMapper orderMapper;

    @Test
    public void testAddOrder() throws Exception {
        final String token = signInAsCustomer();

        willReturn(Optional.of(userMapper.sourceToDestination(CustomersMock.getById(2))))
            .given(userRepository)
            .findById(2);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(2))))
            .given(beerRepository)
            .findById(2);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(3))))
            .given(beerRepository)
            .findById(3);
        willReturn(orderMapper.sourceToDestination(OrderMock.getById(2))).given(orderRepository)
            .save(any(OrderEntity.class));
        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                mapper.writeValueAsString(
                                    OrderRequest.builder()
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
                                        .build())))
            .andExpect(status().isCreated())
            .andExpect(content().json(
                mapper.writeValueAsString(
                    OrderMock.getById(2))
                   ));
    }

    @Test
    public void testChangeOrderStatusById() throws Exception {
        final String token = signInAsCustomer();
        final OrderEntity orderEntity = orderMapper.sourceToDestination(OrderMock.getById(2));

        willReturn(Optional.of(orderEntity)).given(orderRepository).findById(2);
        willReturn(orderEntity).given(orderRepository).save(any(OrderEntity.class));

        mockMvc.perform(patch("/api/orders/2")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" +
                                     "    \"processed\": true\n" +
                                     "}"))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(2)));
    }

    @Test
    public void testShowAllOrders() throws Exception {
        final String token = signInAsCustomer();

        willReturn(OrderMock.getAllValues()
                       .stream()
                       .map(orderMapper::sourceToDestination)
                       .collect(Collectors.toList())
                  ).given(orderRepository).findAll();

        mockMvc.perform(get("/api/orders").header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(
                    OrderMock.getAllValues())));
    }
}
