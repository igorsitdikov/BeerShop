package com.gp.beershop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "All details about the kind of beer and amount.")
@EqualsAndHashCode
public class CustomerOrder {
    @Schema(description = "Kind of beer")
    private Beer beer;

    @Schema(description = "Amount of beer in liters")
    private Integer amount;
}
