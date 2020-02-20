package com.gp.beershop.mapper;

import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.entity.CustomerOrderEntity;
import com.gp.beershop.entity.OrderEntity;
import com.gp.beershop.mock.OrderMock;
import com.gp.beershop.repository.CustomerOrderRepository;
import com.gp.beershop.repository.OrderRepository;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Log
public class CustomerOrderMapperMockTest {
    private final Integer ID = 1;

    @SpyBean
    private CustomerOrderMapper customerOrderMapper;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCustomerOrderDtoToCustomerOrderEntity() {
        final CustomerOrder customerOrder = OrderMock.getById(ID).getCustomerOrders().get(ID);

        final CustomerOrderEntity customerOrderEntity = customerOrderMapper.sourceToDestination(customerOrder);

        assertEquals(customerOrder.getCount(), customerOrderEntity.getCount());
        assertEquals(customerOrder.getBeer().getId(), customerOrderEntity.getBeer().getId());
        assertEquals(customerOrder.getBeer().getAlcohol(), customerOrderEntity.getBeer().getAlcohol());
        assertEquals(customerOrder.getBeer().getName(), customerOrderEntity.getBeer().getName());
        assertEquals(customerOrder.getBeer().getType(), customerOrderEntity.getBeer().getType());
        assertEquals(customerOrder.getBeer().getInStock(), customerOrderEntity.getBeer().getInStock());
        assertEquals(customerOrder.getBeer().getDescription(), customerOrderEntity.getBeer().getDescription());
        assertEquals(customerOrder.getBeer().getCountry(), customerOrderEntity.getBeer().getCountry());
        assertEquals(customerOrder.getBeer().getDensity(), customerOrderEntity.getBeer().getDensity());
        assertEquals(customerOrder.getBeer().getPrice(), customerOrderEntity.getBeer().getPrice());
    }

    @Test
    public void testCustomerOrderEntityToCustomerOrderDto() {
        final CustomerOrder customerOrderExpected = OrderMock.getById(ID).getCustomerOrders().get(ID);

        final BeerEntity beerEntity = new BeerEntity();
        beerEntity.setName(customerOrderExpected.getBeer().getName());
        beerEntity.setId(customerOrderExpected.getBeer().getId());
        beerEntity.setAlcohol(customerOrderExpected.getBeer().getAlcohol());
        beerEntity.setType(customerOrderExpected.getBeer().getType());
        beerEntity.setCountry(customerOrderExpected.getBeer().getCountry());
        beerEntity.setPrice(customerOrderExpected.getBeer().getPrice());
        beerEntity.setDensity(customerOrderExpected.getBeer().getDensity());
        beerEntity.setInStock(customerOrderExpected.getBeer().getInStock());
        beerEntity.setDescription(customerOrderExpected.getBeer().getDescription());

        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProcessed(false);
        orderEntity.setTotal(25D);
        orderRepository.save(orderEntity);

        final CustomerOrderEntity customerOrderEntity = new CustomerOrderEntity();
        customerOrderEntity.setCount(customerOrderExpected.getCount());
        customerOrderEntity.setBeer(beerEntity);
        orderEntity.setCustomerOrders(Set.of(customerOrderEntity));
        customerOrderEntity.setOrders(orderEntity);
        beerEntity.setCustomerOrders(Set.of(customerOrderEntity));
        customerOrderRepository.save(customerOrderEntity);

        final CustomerOrder customerOrderActual = customerOrderMapper.destinationToSource(customerOrderEntity);

        assertEquals(customerOrderExpected, customerOrderActual);
    }
}
