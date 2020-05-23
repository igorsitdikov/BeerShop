package com.gp.beershop.controller;

import com.gp.beershop.dto.Orders;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.entity.OrderEntity;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.mapper.OrderMapper;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.repository.BeerRepository;
import com.gp.beershop.repository.OrderRepository;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
import mock.BeerMock;
import mock.OrderMock;
import mock.OrderRequestMock;
import mock.UsersMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private UserMapper userMapper;
    @SpyBean
    private BeerMapper beerMapper;
    @SpyBean
    private OrderMapper orderMapper;

    @BeforeEach
    public void initAuthorizedUser() {
        final UserEntity customer = userMapper.sourceToDestination(UsersMock.getById(CUSTOMER));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setUserRole(UserRole.CUSTOMER);

        willReturn(Optional.of(customer)).given(userRepository).findByEmail(customer.getEmail());

        final UserEntity admin = userMapper.sourceToDestination(UsersMock.getById(ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setUserRole(UserRole.ADMIN);

        willReturn(Optional.of(admin)).given(userRepository).findByEmail(admin.getEmail());

        willReturn(Optional.of(customer)).given(userRepository).findById(CUSTOMER);
        willReturn(Optional.of(admin)).given(userRepository).findById(ADMIN);
        final BeerEntity alivaria = beerMapper.sourceToDestination(BeerMock.getById(ALIVARIA));
        final BeerEntity pilsner = beerMapper.sourceToDestination(BeerMock.getById(PILSNER));
        final List<BeerEntity> beers = new ArrayList<>(Arrays.asList(alivaria, pilsner));

        final List<Long> ids = new ArrayList<>(Arrays.asList(ALIVARIA, PILSNER));

        willReturn(beers).given(beerRepository).findByBeerIds(ids);
    }


    @Test
    public void testAddOrderAsCustomer() throws Exception {
        // given
        final String token = signInAsUser(false);
        final Orders orderExpected = OrderMock.getById(ORDER_ID);

        willReturn(orderMapper.sourceToDestination(orderExpected))
            .given(orderRepository)
            .save(any(OrderEntity.class));
        // when
        final String responseOrder =
            mockMvc.perform(post("/api/orders")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestByPetr())))
                // then
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        final Orders orderActual = mapper.readValue(responseOrder, Orders.class);

        assertEquals(orderExpected, orderActual);
        assertEquals(sortCustomerOrders(orderExpected), sortCustomerOrders(orderActual));
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    public void testAddOrderAsCustomer_CustomerNotFound() throws Exception {
        // given
        final String token = signInAsUser(false);
        willReturn(Optional.empty()).given(userRepository).findById(CUSTOMER);
        // when
        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                OrderRequestMock.getOrderRequestByPetr())))
            // then
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"No customer with id = " +
                                      CUSTOMER + " was found.\"}"));
        verify(userRepository, times(1)).findById(CUSTOMER);
    }

    @Test
    public void testAddOrderAsCustomer_EmptyOrder() throws Exception {
        // given
        final String token = signInAsUser(false);
        final Orders order = OrderMock.getById(ORDER_ID);
        final OrderEntity orderEntity = orderMapper.sourceToDestination(order);
        orderEntity.setCustomerOrders(new HashSet<>());
        // when
        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                OrderRequestMock.getOrderRequestWithEmptyOrder())))
            // then
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"errorMessage\":\"Order is empty!\"}"));
        verify(orderRepository, times(0)).save(any(OrderEntity.class));
    }

    @Test
    public void testAddOrderAsCustomer_CustomerHasNoPermissions() throws Exception {
        // given
        final String token = signInAsUser(false);

        willReturn(Optional.of(userMapper.sourceToDestination(UsersMock.getById(1L))))
            .given(userRepository)
            .findById(1L);
        // when
        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                OrderRequestMock.getOrderRequestByIvan())))
            // then
            .andExpect(status().isForbidden())
            .andExpect(content().json("{" +
                                      "\"errorMessage\":\"Customer with email = petr.petrov@yandex.ru " +
                                      "tried add order to other account.\"" +
                                      "}"));
        verify(userRepository, times(4)).findByEmail("petr.petrov@yandex.ru");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddOrderAsCustomer_BeerNotFound() throws Exception {
        // given
        final String token = signInAsUser(false);
        final Orders order = OrderMock.getById(ORDER_ID);

        final BeerEntity pilsner = beerMapper.sourceToDestination(BeerMock.getById(PILSNER));
        final List<BeerEntity> beers = new ArrayList<>(Collections.singletonList(pilsner));

        final List<Long> ids = new ArrayList<>(Arrays.asList(ALIVARIA, PILSNER));

        willReturn(beers).given(beerRepository).findByBeerIds(ids);
        willReturn(orderMapper.sourceToDestination(order)).given(orderRepository).save(any(OrderEntity.class));
        // when
        mockMvc.perform(post("/api/orders")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(OrderRequestMock.getOrderRequestByPetr())))
            // then
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"No beer with id = " +
                                      ALIVARIA + " was found.\"}"));
    }

    @Test
    public void testAddOrderAsAdmin() throws Exception {
        // given
        final String token = signInAsUser(true);
        final Orders orderExpected = OrderMock.getById(ORDER_ID);

        willReturn(orderMapper.sourceToDestination(orderExpected))
            .given(orderRepository)
            .save(any(OrderEntity.class));

        // when
        final String responseOrder =
            mockMvc.perform(post("/api/orders")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestByAdmin())))
                // then
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        final Orders orderActual = mapper.readValue(responseOrder, Orders.class);

        assertEquals(orderExpected, orderActual);
        assertEquals(sortCustomerOrders(orderExpected), sortCustomerOrders(orderActual));
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    public void testChangeOrderStatusById() throws Exception {
        // given
        final String token = signInAsUser(true);
        final OrderEntity orderEntity = orderMapper.sourceToDestination(OrderMock.getById(ORDER_ID));
        willReturn(Optional.of(orderEntity)).given(orderRepository).findById(ORDER_ID);
        // when
        mockMvc.perform(patch("/api/orders/" + ORDER_ID)
                            .header("Authorization", token)
                            .param("status", "true")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(ORDER_ID)));
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(orderRepository, times(1)).findById(ORDER_ID);
    }

    @Test
    public void testChangeOrderStatusById_OrderNotFound() throws Exception {
        // given
        final String token = signInAsUser(true);
        // when
        mockMvc.perform(patch("/api/orders/" + ORDER_ID)
                            .header("Authorization", token)
                            .param("status", "true")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isNotFound())
            .andExpect(content().json("{\"errorMessage\":\"No order with id = " +
                                      ORDER_ID + " was found.\"}"));
    }

    @Test
    public void testShowAllOrders() throws Exception {
        // given
        final String token = signInAsUser(true);

        willReturn(OrderMock.getAllValues()
                       .stream()
                       .map(orderMapper::sourceToDestination)
                       .collect(Collectors.toList()))
            .given(orderRepository).findAll();
        // when
        mockMvc.perform(get("/api/orders").header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            //then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(OrderMock.getAllValues())));
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteOrderByIdIsOk() throws Exception {
        // given
        final String token = signInAsUser(true);
        willReturn(true).given(orderRepository).existsById(ORDER_ID);
        // when
        mockMvc.perform(delete("/api/orders/" + ORDER_ID)
                            .header("Authorization", token)
                            .param("status", "true")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk());
        verify(orderRepository, times(1)).existsById(ORDER_ID);
        verify(orderRepository, times(1)).deleteById(ORDER_ID);
    }

    @Test
    public void testDeleteOrderById_OrderNotFound() throws Exception {
        // given
        final String token = signInAsUser(true);
        willReturn(false).given(orderRepository).existsById(ORDER_ID);
        // when
        mockMvc.perform(delete("/api/orders/" + ORDER_ID)
                            .header("Authorization", token)
                            .param("status", "true")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isNotFound());
        verify(orderRepository, times(1)).existsById(ORDER_ID);
        verify(orderRepository, times(0)).deleteById(ORDER_ID);
    }

    @Test
    public void testCancelOrder() throws Exception {
        //given
        final String token = signInAsUser(false);
        final OrderEntity orderEntity = orderMapper.sourceToDestination(OrderMock.getById(ORDER_ID));
        willReturn(Optional.of(orderEntity)).given(orderRepository).findById(ORDER_ID);
        //when
        mockMvc.perform(patch("/api/orders/" + ORDER_ID)
                            .param("canceled", "true")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            //then
            .andExpect(status().isOk());
    }

    @Test
    public void testCancelOrder_UserHasNoPermissions() throws Exception {
        //given
        final OrderEntity orderEntity = orderMapper.sourceToDestination(OrderMock.getById(1L));
        willReturn(Optional.of(orderEntity)).given(orderRepository).findById(1L);
        final UserEntity ivan = userMapper.sourceToDestination(UsersMock.getById(1L));
        ivan.setUserRole(UserRole.CUSTOMER);
        willReturn(Optional.of(ivan)).given(userRepository).findByEmail(ivan.getEmail());
        willReturn(Optional.of(ivan)).given(userRepository).findById(ivan.getId());
        final String token = signInAsUser(false);
        //when
        mockMvc.perform(patch("/api/orders/" + 1)
                            .param("canceled", "true")
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            //then
            .andExpect(status().isForbidden());
    }
}
