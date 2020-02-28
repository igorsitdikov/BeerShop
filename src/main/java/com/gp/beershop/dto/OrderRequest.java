package com.gp.beershop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Builder
@ApiModel(description = "Order request with customer ID and set of goods, which he would to buy")
public class OrderRequest {
    @ApiModelProperty(notes = "Customer ID, which database generated")
    @NotNull(message = "Should input user id")
    private Integer customerId;
    @ApiModelProperty(notes = "Set of goods")
    @NotNull(message = "Should input set of goods")
    private Set<Goods> goods;
}
