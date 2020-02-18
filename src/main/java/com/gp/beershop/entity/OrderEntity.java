package com.gp.beershop.entity;

import com.gp.beershop.mapper.CustomerOrderMapper;
import com.gp.beershop.mapper.UserMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.Set;

@Getter
@Setter
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
}
