package com.gp.beershop.entity;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.Customer;
import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.mapper.CustomerOrderMapper;
import com.gp.beershop.mapper.UserMapper;
import lombok.Data;
import lombok.extern.java.Log;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Log
@Entity(name = "orders")
public class OrderEntity {
    @Transient
    private UserMapper userMapper;
    @Transient
    private CustomerOrderMapper customerOrderMapper;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn
    private UserEntity user;
    private Boolean processed;
    private Double total;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CustomerOrderEntity> customerOrders;

    public Orders convertToOrders() {
        return Orders.builder()
            .id(id)
            .total(total)
            .customer(Customer.builder()
                          .id(1).build())
            .processed(processed)
            .customerOrders(
                customerOrders.stream()
                    .map(customerOrderMapper::destinationToSource).collect(Collectors.toList()))
            .build();
    }

//    @ManyToMany(mappedBy = "orders")
//    private Set<CustomerOrderEntity> customerOrders = new HashSet<>();
}
