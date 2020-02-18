package com.gp.beershop.mapper;

import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.entity.CustomerOrderEntity;
import com.gp.beershop.entity.OrderEntity;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.mock.CustomersMock;
import com.gp.beershop.mock.OrderMock;
import com.gp.beershop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class OrderTestMapperMockTest {
    private final Integer ID = 1;

    @SpyBean
    private UserMapper userMapper;
    @SpyBean
    private BeerMapper beerMapper;
    @SpyBean
    private CustomerOrderMapper customerOrderMapper;
    @SpyBean
    private OrderMapper orderMapper;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    public void testConvertOrderDtoToOrderEntity() {
        final Orders ordersExpected = OrderMock.getById(ID);

        final OrderEntity orderEntityActual = orderMapper.sourceToDestination(ordersExpected);

        assertEquals(ordersExpected.getId(), orderEntityActual.getId());
        assertEquals(ordersExpected.getTotal(), orderEntityActual.getTotal());
        assertEquals(ordersExpected.getProcessed(), orderEntityActual.getProcessed());
        assertEquals(ordersExpected.getCustomer().getEmail(), orderEntityActual.getUser().getEmail());
        assertEquals(ordersExpected.getCustomer().getName(), orderEntityActual.getUser().getName());
        assertEquals(ordersExpected.getCustomer().getPhone(), orderEntityActual.getUser().getPhone());
        assertEquals(ordersExpected.getCustomerOrders().size(), orderEntityActual.getCustomerOrders().size());
    }

    @Test
    @Transactional
    public void testConvertOrderEntityToOrdersDto() {
        final Orders ordersExpected = OrderMock.getById(ID);

        final UserEntity userEntity = userMapper.sourceToDestination(CustomersMock.getById(ID));

        final CustomerOrder customerOrder1 = ordersExpected.getCustomerOrders().get(0);
        final CustomerOrder customerOrder2 = ordersExpected.getCustomerOrders().get(1);
        final BeerEntity beerEntity1 = beerMapper.sourceToDestination(customerOrder1.getBeer());
        final BeerEntity beerEntity2 = beerMapper.sourceToDestination(customerOrder2.getBeer());

        final CustomerOrderEntity customerOrderEntity1 = customerOrderMapper.sourceToDestination(customerOrder1);
        beerEntity1.setCustomerOrders(Set.of(customerOrderEntity1));

        final CustomerOrderEntity customerOrderEntity2 = customerOrderMapper.sourceToDestination(customerOrder2);
        beerEntity2.setCustomerOrders(Set.of(customerOrderEntity2));

        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTotal(ordersExpected.getTotal());
        orderEntity.setUser(userEntity);
        orderEntity.setProcessed(ordersExpected.getProcessed());
        customerOrderEntity1.setOrders(orderEntity);
        customerOrderEntity2.setOrders(orderEntity);
        orderEntity.setCustomerOrders(Set.of(customerOrderEntity1, customerOrderEntity2));
        userEntity.setOrders(Set.of(orderEntity));
        orderRepository.save(orderEntity);

        final Orders ordersActual = orderMapper.destinationToSource(orderEntity);

        assertEquals(ordersExpected.getId(), ordersActual.getId());
        assertEquals(ordersExpected.getTotal(), ordersActual.getTotal());
        assertEquals(ordersExpected.getProcessed(), ordersActual.getProcessed());
        assertEquals(ordersExpected.getCustomer().getEmail(), ordersActual.getCustomer().getEmail());
        assertEquals(ordersExpected.getCustomer().getName(), ordersActual.getCustomer().getName());
        assertEquals(ordersExpected.getCustomer().getPhone(), ordersActual.getCustomer().getPhone());
        assertEquals(ordersExpected.getCustomerOrders().size(), ordersActual.getCustomerOrders().size());
    }
}
