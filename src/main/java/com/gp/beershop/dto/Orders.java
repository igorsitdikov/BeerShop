package com.gp.beershop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@ApiModel(description = "All details about the order")
public class Orders {
    @ApiModelProperty(notes = "The database generated order ID")
    private Integer id;
    @ApiModelProperty(notes = "Customer who made order")
    private UserDTO userDTO;
    @ApiModelProperty(notes = "Order status")
    private Boolean processed;
    @ApiModelProperty(notes = "Order canceled")
    private Boolean canceled;
    @ApiModelProperty(notes = "Order cost")
    private BigDecimal total;
    @ApiModelProperty(notes = "List of goods and their amounts")
    private List<CustomerOrder> customerOrders;
}
