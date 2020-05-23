package com.gp.beershop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@ApiModel(description = "All details about the order")
@EqualsAndHashCode
public class Orders {
    @ApiModelProperty(notes = "The database generated order ID")
    private Long id;

    @ApiModelProperty(notes = "Customer who made order")
    private User user;

    @ApiModelProperty(notes = "Order status")
    private Boolean processed;

    @ApiModelProperty(notes = "Order canceled")
    private Boolean canceled;

    @ApiModelProperty(notes = "Order cost")
    private BigDecimal total;

    @ApiModelProperty(notes = "List of goods and their amounts")
    @EqualsAndHashCode.Exclude
    private List<CustomerOrder> customerOrders;
}
