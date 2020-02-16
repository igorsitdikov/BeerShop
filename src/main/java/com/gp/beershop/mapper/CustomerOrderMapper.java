package com.gp.beershop.mapper;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.entity.CustomerOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper {

    CustomerOrderEntity sourceToDestination(CustomerOrder source);

    CustomerOrder destinationToSource(CustomerOrderEntity destination);
}