package com.gp.beershop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "All details about the Beer.")
@EqualsAndHashCode
public class Beer {
    @ReadOnlyProperty
    @Schema(description = "The database generated beer ID")
    private Long id;

    @Schema(description = "The beer type")
    @NotNull(message = "Should input beer type")
    private String type;

    @Schema(description = "The beer in stock or not")
    @NotNull(message = "Should input beer in stock")
    private Boolean inStock;

    @Schema(description = "The beer name")
    @NotNull(message = "Should input beer name")
    private String name;

    @Schema(description = "The beer description")
    @NotNull(message = "Should input beer description")
    private String description;

    @Schema(description = "The beer alcohol")
    @NotNull(message = "Should input beer alcohol")
    private Double alcohol;

    @Schema(description = "The beer density")
    @NotNull(message = "Should input beer density")
    private Double density;

    @Schema(description = "The country where beer was created")
    @NotNull(message = "Should input beer country")
    private String country;

    @Schema(description = "The beer price")
    @NotNull(message = "Should input beer price")
    private BigDecimal price;
}
