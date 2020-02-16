package com.gp.beershop.entity;

import com.gp.beershop.dto.Beer;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "customer_order")
public class CustomerOrderEntity {
    @Id
    private Integer id;
    private Integer total;
    @ManyToOne
    @JoinColumn
    private OrderEntity order;
    @ManyToOne
    @JoinColumn
    private BeerEntity beer;
}
