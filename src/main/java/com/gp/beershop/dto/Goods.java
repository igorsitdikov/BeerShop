package com.gp.beershop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Goods ID and amount.")
public class Goods {
    @Schema(description = "Goods ID")
    private Long id;

    @Schema(description = "Good amount")
    private Integer amount;
}
