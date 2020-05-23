package com.gp.beershop.entity;

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
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Log
@Entity(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private UserEntity user;

    private boolean processed;

    private boolean canceled;

    private BigDecimal total;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CustomerOrderEntity> customerOrders;
}
