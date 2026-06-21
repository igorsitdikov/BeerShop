package com.gp.beershop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@Schema(description = "All details about the order")
@EqualsAndHashCode
public class Orders {
    @Schema(description = "The database generated order ID")
    private Long id;

    @Schema(description = "Customer who made order")
    private User user;

    @Schema(description = "Order status")
    private Boolean processed;

    @Schema(description = "Order canceled")
    private Boolean canceled;

    @Schema(description = "Order cost")
    private BigDecimal total;

    @Schema(description = "List of goods and their amounts")
    @EqualsAndHashCode.Exclude
    private List<CustomerOrder> customerOrders;
}
