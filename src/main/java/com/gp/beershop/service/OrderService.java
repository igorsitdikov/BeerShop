package com.gp.beershop.service;

import com.gp.beershop.dto.Goods;
import com.gp.beershop.dto.OrderRequest;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.entity.CustomerOrderEntity;
import com.gp.beershop.entity.OrderEntity;
import com.gp.beershop.entity.UserEntity;
import com.gp.beershop.exception.NoSuchBeerException;
import com.gp.beershop.exception.NoSuchOrderException;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.OrderIsEmptyException;
import com.gp.beershop.exception.SuchUserHasNoPermissionsException;
import com.gp.beershop.mapper.OrderMapper;
import com.gp.beershop.mapper.UserMapper;
import com.gp.beershop.repository.BeerRepository;
import com.gp.beershop.repository.OrderRepository;
import com.gp.beershop.repository.UserRepository;
import com.gp.beershop.security.JwtUtil;
import com.gp.beershop.security.UserRole;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Log
@Service
public class OrderService {
    private static final Integer BEARER = 7;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BeerRepository beerRepository;
    private final BeerService beerService;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public Orders addOrder(final OrderRequest orderRequest, final String token)
            throws NoSuchUserException, NoSuchBeerException, OrderIsEmptyException, SuchUserHasNoPermissionsException {

        final UserEntity userEntity = userRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new NoSuchUserException(
                        "No customer with id = " + orderRequest.getCustomerId() + " was found."));

        final String userEmailFromToken = jwtUtil.extractUsername(token.substring(BEARER));

        if (!userEntity.getEmail().equals(userEmailFromToken)) {
            throw new SuchUserHasNoPermissionsException(
                    "Customer with email = " + userEmailFromToken + " tried add order to other account.");
        }

        final Set<Goods> goodsIds = orderRequest.getGoods();
        final OrderEntity orderEntity = new OrderEntity();
        final Set<CustomerOrderEntity> customerOrders = findAllCustomerOrders(goodsIds, orderEntity);

        final BigDecimal total = calculateTotalCostOrder(customerOrders);
        orderEntity.setUser(userEntity);
        orderEntity.setTotal(total);
        orderEntity.setCustomerOrders(customerOrders);
        final OrderEntity orderSaved = orderRepository.save(orderEntity);
        return orderMapper.destinationToSource(orderSaved);
    }

    private BigDecimal calculateTotalCostOrder(final Set<CustomerOrderEntity> customerOrders) {
        return customerOrders
                .stream()
                .map(order -> order.getBeer().getPrice().multiply(BigDecimal.valueOf(order.getAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<CustomerOrderEntity> findAllCustomerOrders(final Set<Goods> goodsSet, final OrderEntity orderEntity)
            throws NoSuchBeerException, OrderIsEmptyException {
        final Set<CustomerOrderEntity> customerOrders = new HashSet<>();

        if (goodsSet.isEmpty()) {
            throw new OrderIsEmptyException("Order is empty!");
        }

        for (final Goods goods : goodsSet) {
            final CustomerOrderEntity customerOrderEntity = new CustomerOrderEntity();
            customerOrderEntity.setAmount(goods.getAmount());
            customerOrderEntity.setBeer(
                    beerRepository
                            .findById(goods.getId())
                            .orElseThrow(() -> new NoSuchBeerException("No beer with id = " + goods.getId() + " was found.")));
            customerOrderEntity.setOrders(orderEntity);
            customerOrders.add(customerOrderEntity);
        }
        return customerOrders;
    }

    @Transactional
    public Integer changeOrderStatus(final Integer id, final String token, final Boolean status, final Boolean canceled)
            throws NoSuchOrderException, SuchUserHasNoPermissionsException {
        final OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchOrderException("No order with id = " + id + " was found."));
        final String userEmailFromToken = jwtUtil.extractUsername(token.substring(BEARER));
        final UserEntity userEntity = userRepository.findByEmail(userEmailFromToken).get();
        final UserEntity userEntityFromOrder = userRepository.findById(orderEntity.getUser().getId()).get();

        if (!userEntityFromOrder.getEmail().equals(userEntity.getEmail()) &&
                userEntity.getUserRole() != UserRole.ADMIN) {
            throw new SuchUserHasNoPermissionsException(
                    "Customer with email = " + userEmailFromToken + " tried cancel order, but has no permissions.");
        }

        if (userEntity.getUserRole() == UserRole.CUSTOMER && !orderEntity.getProcessed()) {
            orderEntity.setCanceled(canceled);
        }
        if (userEntity.getUserRole() == UserRole.ADMIN) {
            orderEntity.setProcessed(status);
        }

        orderRepository.save(orderEntity);
        return orderEntity.getId();
    }

    @Transactional
    public void deleteOrder(final Integer orderId) throws NoSuchOrderException {
        final boolean isFound = orderRepository.existsById(orderId);
        if (!isFound) {
            throw new NoSuchOrderException("No order with id = " + orderId + " was found.");
        }
        orderRepository.deleteById(orderId);
    }

    public List<Orders> showOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::destinationToSource)
                .collect(Collectors.toList());
    }

    public void cancelOrder(final Integer orderId) throws NoSuchOrderException {
        final OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchOrderException("No order with id = " + orderId + " was found."));
        orderEntity.setCanceled(true);
        orderRepository.save(orderEntity);
    }
}
