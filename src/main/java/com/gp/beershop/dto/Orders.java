package com.gp.beershop.dto;

import com.gp.beershop.entity.OrderEntity;
import com.gp.beershop.mapper.CustomerOrderMapper;
import com.gp.beershop.mapper.UserMapper;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class Orders {
    private final CustomerOrderMapper customerOrderMapper;
    private final UserMapper userMapper;
    private Integer id;
    private Customer customer;
    private Boolean processed;
    private Double total;
    private List<CustomerOrder> customerOrders;
//    public OrderEntity convertToOrderEntity() {
//        final OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setId(id);
//        orderEntity.setTotal(total);
//        orderEntity.setProcessed(processed);
//        orderEntity.setUser(userMapper.sourceToDestination(customer));
//        orderEntity.setCustomerOrders(customerOrder.stream().map(customerOrderMapper::sourceToDestination).collect(Collectors.toSet()));
//        return orderEntity;
//    }
}
