package com.gp.beershop.mapper;

import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.entity.CustomerOrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper {

    CustomerOrderEntity sourceToDestination(CustomerOrder source);

    CustomerOrder destinationToSource(CustomerOrderEntity destination);
}