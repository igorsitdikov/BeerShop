package com.gp.beershop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@ApiModel(description = "All details about the kind of beer and amount.")
@EqualsAndHashCode
public class CustomerOrder {
    @ApiModelProperty(notes = "Kind of beer")
    private Beer beer;

    @ApiModelProperty(notes = "Amount of beer in liters")
    private Integer amount;
}
