package com.gp.beershop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "beer")
public class BeerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Boolean inStock;

    private String name;

    private String description;

    private Double alcohol;

    private Double density;

    private String country;

    private BigDecimal price;

    @OneToMany(mappedBy = "beer")
    private Set<CustomerOrderEntity> customerOrders = new HashSet<>();
}


