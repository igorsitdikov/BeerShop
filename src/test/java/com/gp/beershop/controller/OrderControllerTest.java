package com.gp.beershop.controller;


import com.gp.beershop.dto.Orders;
import com.gp.beershop.dto.UserDTO;
import com.gp.beershop.entity.OrderEntity;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.mapper.OrderMapper;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.mock.BeerMock;
import com.gp.beershop.mock.OrderMock;
import com.gp.beershop.mock.OrderRequestMock;
import com.gp.beershop.mock.UsersMock;
import com.gp.beershop.repository.BeerRepository;
import com.gp.beershop.repository.OrderRepository;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends AbstractControllerTest {
    private final Integer CUSTOMER = 2;
    private final Integer ADMIN = 3;

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
    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void initAuthorizedUser() {
        final UserEntity customer = userMapper.sourceToDestination(UsersMock.getById(CUSTOMER));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setUserRole(UserRole.CUSTOMER);

        willReturn(Optional.of(customer))
            .given(userRepository)
            .findByEmail(customer.getEmail());

        final UserEntity admin = userMapper.sourceToDestination(UsersMock.getById(ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setUserRole(UserRole.ADMIN);

        willReturn(Optional.of(admin))
            .given(userRepository)
            .findByEmail(admin.getEmail());
    }


    @Test
    public void testAddOrderAsCustomer() throws Exception {
        final String token = signInAsUser(false);
        final Orders order = OrderMock.getById(2);
        willReturn(Optional.of(userMapper.sourceToDestination(UsersMock.getById(2))))
            .given(userRepository)
            .findById(2);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(2))))
            .given(beerRepository)
            .findById(2);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(3))))
            .given(beerRepository)
            .findById(3);
        willReturn(orderMapper.sourceToDestination(order))
            .given(orderRepository)
            .save(any(OrderEntity.class));
        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestByPetr())))
            .andExpect(status().isCreated())
            .andExpect(content().json(
                mapper.writeValueAsString(
                    Orders.builder()
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
                        .build())));
    }

    @Test
    public void testAddOrderAsCustomer_CustomerNotFound() throws Exception {
        final String token = signInAsUser(false);
        final Orders order = OrderMock.getById(2);

        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(2))))
            .given(beerRepository)
            .findById(2);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(3))))
            .given(beerRepository)
            .findById(3);
        willReturn(orderMapper.sourceToDestination(order))
            .given(orderRepository)
            .save(any(OrderEntity.class));
        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestByPetr())))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"errorMessage\":\"No customer with id = 2 was found.\"}"));
    }

    @Test
    public void testAddOrderAsCustomer_EmptyOrder() throws Exception {
        final String token = signInAsUser(false);
        final Orders order = OrderMock.getById(2);
        final OrderEntity orderEntity = orderMapper.sourceToDestination(order);
        orderEntity.setCustomerOrders(new HashSet<>());
        willReturn(Optional.of(userMapper.sourceToDestination(UsersMock.getById(2))))
            .given(userRepository)
            .findById(2);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(2))))
            .given(beerRepository)
            .findById(2);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(3))))
            .given(beerRepository)
            .findById(3);
        willReturn(orderEntity)
            .given(orderRepository)
            .save(any(OrderEntity.class));
        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestWithEmptyOrder())))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"errorMessage\":\"Order is empty!\"}"));
    }

    @Test
    public void testAddOrderAsCustomer_CustomerHasNoPermissions() throws Exception {
        final String token = signInAsUser(false);

        willReturn(Optional.of(userMapper.sourceToDestination(UsersMock.getById(1))))
            .given(userRepository)
            .findById(1);
        willReturn(Optional.of(userMapper.sourceToDestination(UsersMock.getById(2))))
            .given(userRepository)
            .findById(2);

        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestByIvan())))
            .andExpect(status().isBadRequest())
            .andExpect(content().json(
                "{\"errorMessage\":\"Customer with email = petr.petrov@yandex.ru tried add order to other account.\"}"));
    }

    @Test
    public void testAddOrderAsCustomer_BeerNotFound() throws Exception {
        final String token = signInAsUser(false);
        final Orders order = OrderMock.getById(2);
        willReturn(Optional.of(userMapper.sourceToDestination(UsersMock.getById(2))))
            .given(userRepository)
            .findById(2);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(3))))
            .given(beerRepository)
            .findById(3);
        willReturn(orderMapper.sourceToDestination(order))
            .given(orderRepository)
            .save(any(OrderEntity.class));
        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestByPetr())))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"errorMessage\":\"No beer with id = 2 was found.\"}"));
    }

    @Test
    public void testAddOrderAsAdmin() throws Exception {
        final String token = signInAsUser(true);

        final Orders order = OrderMock.getById(2);
        willReturn(Optional.of(userMapper.sourceToDestination(UsersMock.getById(3))))
            .given(userRepository)
            .findById(3);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(2))))
            .given(beerRepository)
            .findById(2);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(3))))
            .given(beerRepository)
            .findById(3);
        willReturn(orderMapper.sourceToDestination(order))
            .given(orderRepository)
            .save(any(OrderEntity.class));
        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestByAdmin())))
            .andExpect(status().isCreated())
            .andExpect(content().json(
                mapper.writeValueAsString(
                    Orders.builder()
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
                        .build())));
    }

    @Test
    public void testChangeOrderStatusById() throws Exception {
        final String token = signInAsUser(true);
        final OrderEntity orderEntity = orderMapper.sourceToDestination(OrderMock.getById(2));

        willReturn(Optional.of(orderEntity)).given(orderRepository).findById(2);
        willReturn(orderEntity).given(orderRepository).save(any(OrderEntity.class));

        mockMvc.perform(patch("/api/orders/2")
                            .header("Authorization", token)
                            .param("status", "true")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(2)));
    }

    @Test
    public void testChangeOrderStatusById_OrderNotFound() throws Exception {
        final String token = signInAsUser(true);
        final OrderEntity orderEntity = orderMapper.sourceToDestination(OrderMock.getById(2));

//        willReturn(Optional.of(orderEntity)).given(orderRepository).findById(2);
        willReturn(orderEntity).given(orderRepository).save(any(OrderEntity.class));

        mockMvc.perform(patch("/api/orders/2")
                            .header("Authorization", token)
                            .param("status", "true")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"errorMessage\":\"No order with id = 2 was found.\"}"));
    }

    @Test
    public void testShowAllOrders() throws Exception {
        final String token = signInAsUser(true);

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
