package com.gp.beershop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;

@Data
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

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "beer", fetch = FetchType.LAZY)
    private Set<CustomerOrderEntity> customerOrders;
}


