package com.gp.beershop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "All details about the Beer.")
@EqualsAndHashCode
public class Beer {
    @ReadOnlyProperty
    @ApiModelProperty(notes = "The database generated beer ID")
    private Integer id;
    @ApiModelProperty(notes = "The beer type")
    @NotNull(message = "Should input beer type")
    private String type;
    @ApiModelProperty(notes = "The beer in stock or not")
    @NotNull(message = "Should input beer in stock")
    private Boolean inStock;
    @ApiModelProperty(notes = "The beer name")
    @NotNull(message = "Should input beer name")
    private String name;
    @ApiModelProperty(notes = "The beer description")
    @NotNull(message = "Should input beer description")
    private String description;
    @ApiModelProperty(notes = "The beer alcohol")
    @NotNull(message = "Should input beer alcohol")
    private Double alcohol;
    @ApiModelProperty(notes = "The beer density")
    @NotNull(message = "Should input beer density")
    private Double density;
    @ApiModelProperty(notes = "The country where beer was created")
    @NotNull(message = "Should input beer country")
    private String country;
    @ApiModelProperty(notes = "The beer price")
    @NotNull(message = "Should input beer price")
    private BigDecimal price;
}
