package com.gp.beershop.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "beer")
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

}
