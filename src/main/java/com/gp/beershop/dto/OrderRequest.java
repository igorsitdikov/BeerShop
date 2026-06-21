package com.gp.beershop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Builder
@Schema(description = "Order request with customer ID and set of goods, which he would to buy")
public class OrderRequest {
    @Schema(description = "Customer ID, which database generated")
    @NotNull(message = "Should input user id")
    private Long customerId;

    @Schema(description = "Set of goods")
    @NotNull(message = "Should input set of goods")
    private Set<Goods> goods;
}
