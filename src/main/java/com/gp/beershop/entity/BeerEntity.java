package com.gp.beershop.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "beer")
public class BeerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private Boolean inStock;
    private String name;
    private String description;
    private Double alcohol;
    private Double density;
    private String country;
    private Double price;
//    @ManyToMany
//    @JoinTable(name = "order_beer",
//            joinColumns = @JoinColumn(name = "beer_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"))
//    private Set<OrderEntity> orders;
    @OneToMany(mappedBy = "beer")
    private Set<CustomerOrderEntity> customerOrders;
}


