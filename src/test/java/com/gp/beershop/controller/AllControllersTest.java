package com.gp.beershop.controller;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.entity.OrderEntity;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.mapper.OrderMapper;
import com.gp.beershop.mapper.UserMapper;
import mock.BeerMock;
import mock.OrderMock;
import mock.OrderRequestMock;
import mock.UsersMock;
import com.gp.beershop.repository.BeerRepository;
import com.gp.beershop.repository.OrderRepository;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AllControllersTest extends AbstractControllerTest {
    private final static Long ANTON = 4L;

    @MockBean
    private BeerRepository beerRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private UserRepository userRepository;
    @SpyBean
    private BeerMapper beerMapper;
    @SpyBean
    private OrderMapper orderMapper;
    @SpyBean
    private UserMapper userMapper;

    @BeforeEach
    public void initRepository() {
        final UserEntity admin = userMapper.sourceToDestination(UsersMock.getById(ADMIN));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setUserRole(UserRole.ADMIN);

        final UserEntity anton = userMapper.sourceToDestination(UsersMock.getById(ANTON));
        anton.setPassword(passwordEncoder.encode(anton.getPassword()));
        anton.setUserRole(UserRole.CUSTOMER);

        final Beer beerKrynica = BeerMock.getById(KRYNICA);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beerKrynica);
        final Orders orderAnton = OrderMock.getById(ANTON);
        final OrderEntity orderEntity = orderMapper.sourceToDestination(OrderMock.getById(4L));

        willReturn(List.of(BeerMock.getById(ALIVARIA), BeerMock.getById(PILSNER), BeerMock.getById(LIDSKOE))
                       .stream()
                       .map(beerMapper::sourceToDestination)
                       .collect(Collectors.toList()))
            .given(beerRepository).findAll();

        willReturn(Optional.of(admin)).given(userRepository).findByEmail(admin.getEmail());

        willReturn(Optional.empty(), Optional.of(anton)).given(userRepository).findByEmail(anton.getEmail());
        willReturn(Optional.of(anton)).given(userRepository).findById(ANTON);
        willReturn(beerEntity).given(beerRepository).save(any(BeerEntity.class));

        willReturn(true).given(beerRepository).existsById(KRYNICA);

        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(ALIVARIA))))
            .given(beerRepository)
            .findById(ALIVARIA);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(PILSNER))))
            .given(beerRepository)
            .findById(PILSNER);

        willReturn(orderMapper.sourceToDestination(orderAnton))
            .given(orderRepository)
            .save(any(OrderEntity.class));

        willReturn(List.of(OrderMock.getById(1L), OrderMock.getById(3L), OrderMock.getById(5L))
                       .stream()
                       .map(orderMapper::sourceToDestination)
                       .collect(Collectors.toList()))
            .given(orderRepository).findAll();

        willReturn(Optional.of(orderEntity)).given(orderRepository).findById(ORDER_ID);
    }

    @Test
    public void testBusinessLogic() throws Exception {
        // given
        final Beer beerKrynica = BeerMock.getById(KRYNICA);
        final Orders orderAntonExpected = OrderMock.getById(ANTON);
        // when
        mockMvc.perform(get("/api/beers").contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(
                    List.of(BeerMock.getById(LIDSKOE), BeerMock.getById(PILSNER), BeerMock.getById(ALIVARIA)))));

        verify(beerRepository, times(1)).findAll();
        // when
        mockMvc.perform(get("/api/beers")
                            .param("type", "темное")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(List.of(BeerMock.getById(2L)))));

        verify(beerRepository, times(2)).findAll();
        // when
        final String responseAdmin = mockMvc.perform(
            post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(
                    new AuthRequest(UsersMock.getById(ADMIN).getEmail(), UsersMock.getById(ADMIN).getPassword()))))
            // then
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        verify(userRepository, times(2)).findByEmail(UsersMock.getById(ADMIN).getEmail());
        // given
        final String tokenAdmin = "Bearer " + mapper.readValue(responseAdmin, UserSignInResponse.class).getToken();
        // when
        final String responseKrynicaId =
            mockMvc.perform(post("/api/beers")
                                .header("Authorization", tokenAdmin)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(beerKrynica)))
                // then
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(KRYNICA)))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        verify(beerRepository, times(1)).save(any(BeerEntity.class));
        // when
        mockMvc.perform(put("/api/beers/" + responseKrynicaId)
                            .header("Authorization", tokenAdmin)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                BeerMock.getById(6L))))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(BeerMock.getById(6L))));

        verify(beerRepository, times(2)).save(any(BeerEntity.class));
        // when
        mockMvc.perform(delete("/api/beers/" + responseKrynicaId)
                            .header("Authorization", tokenAdmin)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk());

        verify(beerRepository, times(1)).deleteById(KRYNICA);
        // when
        final String responseAnton =
            mockMvc.perform(post("/api/users/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(
                                    UsersMock.getById(4L))))
                // then
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // given
        final String tokenAnton = "Bearer " + mapper.readValue(responseAnton, UserSignInResponse.class).getToken();

        verify(userRepository, times(1)).save(any(UserEntity.class));
        // when
        final String responseOrderAnton =
            mockMvc.perform(post("/api/orders")
                                .header("Authorization", tokenAnton)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestByAnton())))
                // then
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        final Orders orderAntonActual = mapper.readValue(responseOrderAnton, Orders.class);

        assertEquals(orderAntonExpected, orderAntonActual);
        assertEquals(sortCustomerOrders(orderAntonExpected), sortCustomerOrders(orderAntonActual));
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        // when
        mockMvc.perform(get("/api/orders").header("Authorization", tokenAdmin)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(OrderMock.getAllValuesBusinessLogic())));

        verify(orderRepository, times(1)).findAll();
        // when
        mockMvc.perform(patch("/api/orders/" + ORDER_ID)
                            .header("Authorization", tokenAdmin)
                            .param("status", "true")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(ORDER_ID)));

        verify(orderRepository, times(2)).save(any(OrderEntity.class));
    }
}
