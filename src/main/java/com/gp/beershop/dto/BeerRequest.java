package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeerRequest {
    private String type;
    private Boolean in_stock;
    private String name;
    private String description;
    private Double alcohol;
    private Double density;
    private String country;
    private Double price;
}
