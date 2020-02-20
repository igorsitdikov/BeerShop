package com.gp.beershop.service;

import com.gp.beershop.dto.Customer;
import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.NoSuchCustomerException;
import com.gp.beershop.exception.NoSuchOrderException;
import com.gp.beershop.mapper.UserMapperImpl;
import com.gp.beershop.mock.CustomersMock;
import com.gp.beershop.mock.OrderMock;
import com.gp.beershop.mock.OrderRequestMock;
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
        final Orders orders = OrderMock.getById(ID);
        Mockito.doReturn(Optional.empty()).when(orderRepository).findById(ID);
        assertThrows(NoSuchOrderException.class, () -> orderService.changeOrderStatus(ID, orders));
    }

    @Test
    public void testAddOrderCustomerNotFound() {
        final OrderRequest orderRequest = OrderRequestMock.getOrderRequest();
        final Integer userId = orderRequest.getCustomerId();
        Mockito.doReturn(Optional.empty()).when(userRepository).findById(userId);
        assertThrows(NoSuchCustomerException.class, () -> orderService.addOrder(orderRequest));
    }

    @Test
    public void testAddOrderBeerNotFound() {
        final OrderRequest orderRequest = OrderRequestMock.getOrderRequest();
        final Integer userId = orderRequest.getCustomerId();
        final Customer customer = CustomersMock.getById(userId);
        final UserEntity user = userMapper.sourceToDestination(customer);
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(userId);
        Mockito.doReturn(Optional.empty()).when(beerRepository).findById(anyInt());
        assertThrows(NoSuchBeerException.class, () -> orderService.addOrder(orderRequest));
    }
}
