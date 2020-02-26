package com.gp.beershop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "All details about the Beer.")
public class Beer {
    @ReadOnlyProperty
    @ApiModelProperty(notes = "The database generated beer ID")
    private Integer id;
    @ApiModelProperty(notes = "The beer type")
    private String type;
    @ApiModelProperty(notes = "The beer in stock or not")
    private Boolean inStock;
    @ApiModelProperty(notes = "The beer name")
    private String name;
    @ApiModelProperty(notes = "The beer description")
    private String description;
    @ApiModelProperty(notes = "The beer alcohol")
    private Double alcohol;
    @ApiModelProperty(notes = "The beer density")
    private Double density;
    @ApiModelProperty(notes = "The country where beer was created")
    private String country;
    @ApiModelProperty(notes = "The beer price")
    private BigDecimal price;
}
