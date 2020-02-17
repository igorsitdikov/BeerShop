package com.gp.beershop.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "customer_order")
public class CustomerOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "order_customer_order",
//        joinColumns = @JoinColumn(name = "customer_order_id", referencedColumnName = "id"),
//        inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"))
//    private Set<OrderEntity> orders;
    @ManyToOne
    @JoinColumn
    private OrderEntity orders;
    @ManyToOne
    @JoinColumn
    private BeerEntity beer;
    private Integer count;
}
