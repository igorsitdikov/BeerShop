package com.gp.beershop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "Goods ID and amount.")
public class Goods {
    @ApiModelProperty(notes = "Goods ID")
    private Long id;

    @ApiModelProperty(notes = "Good amount")
    private Integer amount;
}
