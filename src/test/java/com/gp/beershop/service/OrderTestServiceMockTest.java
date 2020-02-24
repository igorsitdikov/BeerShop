package com.gp.beershop.service;

import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.UserDTO;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.NoSuchOrderException;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.OrderIsEmptyException;
import com.gp.beershop.mapper.UserMapperImpl;
import com.gp.beershop.mock.OrderRequestMock;
import com.gp.beershop.mock.UsersMock;
import com.gp.beershop.repository.BeerRepository;
import com.gp.beershop.repository.OrderRepository;
import com.gp.beershop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class OrderTestServiceMockTest {

    private static final int ID = 1;
    @Mock
    private OrderRepository orderRepository;
    @Spy
    private UserMapperImpl userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BeerRepository beerRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    public void testChangeStatusOrderNotFound() {
        Mockito.doReturn(Optional.empty()).when(orderRepository).findById(ID);
        assertThrows(NoSuchOrderException.class, () -> orderService.changeOrderStatus(ID, Boolean.TRUE));
    }

    @Test
    public void testAddOrderCustomerNotFound() {
        final OrderRequest orderRequest = OrderRequestMock.getOrderRequest();
        final Integer userId = orderRequest.getCustomerId();
        Mockito.doReturn(Optional.empty()).when(userRepository).findById(userId);
        assertThrows(NoSuchUserException.class, () -> orderService.addOrder(orderRequest));
    }

    @Test
    public void testAddOrderEmptyOrder() {
        final OrderRequest orderRequest = OrderRequest.builder()
            .customerId(ID)
            .goods(new HashSet<>())
            .build();
        Mockito.doReturn(Optional.of(userMapper.sourceToDestination(UsersMock.getById(ID))))
            .when(userRepository)
            .findById(ID);
        assertThrows(OrderIsEmptyException.class, () -> orderService.addOrder(orderRequest));
    }

    @Test
    public void testAddOrderBeerNotFound() {
        final OrderRequest orderRequest = OrderRequestMock.getOrderRequest();
        final Integer userId = orderRequest.getCustomerId();
        final UserDTO userDTO = UsersMock.getById(userId);
        final UserEntity user = userMapper.sourceToDestination(userDTO);
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(userId);
        Mockito.doReturn(Optional.empty()).when(beerRepository).findById(anyInt());
        assertThrows(NoSuchBeerException.class, () -> orderService.addOrder(orderRequest));
    }
}
