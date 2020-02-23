package com.gp.beershop.service;

import com.gp.beershop.dto.Goods;
import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.entity.CustomerOrderEntity;
import com.gp.beershop.entity.OrderEntity;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.NoSuchOrderException;
import com.gp.beershop.mapper.OrderMapper;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.repository.BeerRepository;
import com.gp.beershop.repository.OrderRepository;
import com.gp.beershop.repository.UserRepository;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Log
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BeerRepository beerRepository;
    private final BeerService beerService;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    public Orders addOrder(final OrderRequest orderRequest) throws NoSuchUserException, NoSuchBeerException {

        final UserEntity userEntity = userRepository.findById(orderRequest.getCustomerId())
            .orElseThrow(() -> new NoSuchUserException(
                "No customer with id = " + orderRequest.getCustomerId() + " was found."));

        final Set<Goods> goodsIds = orderRequest.getGoods();
        final Set<CustomerOrderEntity> customerOrders = findAllCustomerOrders(goodsIds);

        final Double total = calculateTotalCostOrder(customerOrders);
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setProcessed(false);
        orderEntity.setUser(userEntity);
        orderEntity.setTotal(total);
        orderEntity.setCustomerOrders(customerOrders);
        final OrderEntity orderSaved = orderRepository.save(orderEntity);
        return orderMapper.destinationToSource(orderSaved);
    }

    @Transactional
    public Integer changeOrderStatus(final Integer id, final Boolean status) throws NoSuchOrderException {
        final OrderEntity orderEntity = orderRepository.findById(id)
            .orElseThrow(() -> new NoSuchOrderException("No order with id = " + id + " was found."));

        orderEntity.setProcessed(status);
        orderRepository.save(orderEntity);
        return orderEntity.getId();
    }

    public List<Orders> showOrders() {
        return orderRepository.findAll()
            .stream()
            .map(orderMapper::destinationToSource)
            .collect(Collectors.toList());
    }

    private Double calculateTotalCostOrder(final Set<CustomerOrderEntity> customerOrders) {
        return customerOrders
            .stream()
            .mapToDouble(order -> order.getBeer().getPrice() * order.getCount())
            .sum();
    }

    private Set<CustomerOrderEntity> findAllCustomerOrders(final Set<Goods> goodsSet) throws NoSuchBeerException {
        final Set<CustomerOrderEntity> customerOrders = new HashSet<>();

        for (final Goods goods : goodsSet) {
            final CustomerOrderEntity customerOrderEntity = new CustomerOrderEntity();
            customerOrderEntity.setCount(goods.getCount());
            customerOrderEntity.setBeer(
                beerRepository.findById(goods.getId()).orElseThrow(
                    () -> new NoSuchBeerException("No beer with id = " + goods.getId() + " was found.")));
            customerOrders.add(customerOrderEntity);
        }
        return customerOrders;
    }
}
