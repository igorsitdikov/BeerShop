package com.gp.beershop.controller;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.dto.UserDTO;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.entity.BeerEntity;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public class AllControllersTest extends AbstractControllerTest {
    private final Integer ADMIN = 3;
    private final Integer ANTON = 4;
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

        final Beer beerKrynica = BeerMock.getById(4);
        final BeerEntity beerEntity = beerMapper.sourceToDestination(beerKrynica);
        final Orders orderAnton = OrderMock.getById(ANTON);
        final OrderEntity orderEntity = orderMapper.sourceToDestination(OrderMock.getById(4));

        willReturn(List.of(BeerMock.getById(1), BeerMock.getById(2), BeerMock.getById(3))
                       .stream()
                       .map(beerMapper::sourceToDestination)
                       .collect(Collectors.toList()))
            .given(beerRepository).findAll();

        willReturn(Optional.of(admin)).given(userRepository).findByEmail(admin.getEmail());

        willReturn(Optional.empty(), Optional.of(anton)).given(userRepository).findByEmail(anton.getEmail());
        willReturn(Optional.of(anton)).given(userRepository).findById(ANTON);
        willReturn(beerEntity).given(beerRepository).save(any(BeerEntity.class));

        willReturn(true).given(beerRepository).existsById(4);

        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(2))))
            .given(beerRepository)
            .findById(2);
        willReturn(Optional.of(beerMapper.sourceToDestination(BeerMock.getById(3))))
            .given(beerRepository)
            .findById(3);

        willReturn(orderMapper.sourceToDestination(orderAnton))
            .given(orderRepository)
            .save(any(OrderEntity.class));

        willReturn(List.of(OrderMock.getById(1), OrderMock.getById(4))
                       .stream()
                       .map(orderMapper::sourceToDestination)
                       .collect(Collectors.toList()))
            .given(orderRepository).findAll();


        willReturn(Optional.of(orderEntity)).given(orderRepository).findById(2);
    }

    @Test
    public void testBusinessLogic() throws Exception {
        final Beer beerKrynica = BeerMock.getById(4);
        final Orders order = OrderMock.getById(ANTON);

        mockMvc.perform(get("/api/beers")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(List.of(BeerMock.getById(1), BeerMock.getById(2), BeerMock.getById(3)))));

        verify(beerRepository, times(1)).findAll();

        mockMvc.perform(get("/api/beers")
                            .param("type", "темное")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(List.of(BeerMock.getById(2)))));

        verify(beerRepository, times(2)).findAll();

        final String responseAdmin = mockMvc.perform(
            post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(
                    AuthRequest.builder()
                        .email(UsersMock.getById(ADMIN).getEmail())
                        .password(UsersMock.getById(ADMIN).getPassword())
                        .build())))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        verify(userRepository, times(2)).findByEmail(UsersMock.getById(ADMIN).getEmail());

        final String tokenAdmin = "Bearer " + mapper.readValue(responseAdmin, UserSignInResponse.class).getToken();

        final String responseKrynicaId =
            mockMvc.perform(post("/api/beers")
                                .header("Authorization", tokenAdmin)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(beerKrynica)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(4)))
                .andReturn().getResponse().getContentAsString();

        verify(beerRepository, times(1)).save(any(BeerEntity.class));

        mockMvc.perform(put("/api/beers/" + responseKrynicaId)
                            .header("Authorization", tokenAdmin)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                BeerMock.getById(6))))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(BeerMock.getById(6))));

        verify(beerRepository, times(2)).save(any(BeerEntity.class));

        mockMvc.perform(delete("/api/beers/" + responseKrynicaId)
                            .header("Authorization", tokenAdmin)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(beerRepository, times(1)).deleteById(4);

        final String responseAnton =
            mockMvc.perform(post("/api/users/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(
                                    UsersMock.getById(4))))
                // then
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        final String tokenAnton = "Bearer " + mapper.readValue(responseAnton, UserSignInResponse.class).getToken();
        verify(userRepository, times(4)).save(any(UserEntity.class));

        mockMvc.perform(post("/api/orders")
                            .header("Authorization", tokenAnton)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestByAnton())))
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
        verify(orderRepository, times(1)).save(any(OrderEntity.class));

        mockMvc.perform(get("/api/orders").header("Authorization", tokenAdmin)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(OrderMock.getAllValuesBusinessLogic())));

        verify(orderRepository, times(1)).findAll();

        mockMvc.perform(patch("/api/orders/2")
                            .header("Authorization", tokenAdmin)
                            .param("status", "true")
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(2)));

        verify(orderRepository, times(2)).save(any(OrderEntity.class));
    }
}
