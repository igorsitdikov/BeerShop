package com.gp.beershop.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Goods {
    private Integer id;
    private Integer count;
}
