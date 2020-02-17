package com.gp.beershop.mapper;

import com.gp.beershop.dto.Orders;
import com.gp.beershop.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mappings({
        @Mapping(target = "id", source = "source.id"),
        @Mapping(target = "customerOrders", source = "source.customerOrders"),
        @Mapping(target = "user", source = "source.customer"),
        @Mapping(target = "processed", source = "source.processed"),
        @Mapping(target = "total", source = "source.total")
    })
    OrderEntity sourceToDestination(Orders source);

    @Mappings({
        @Mapping(target = "id", source = "destination.id"),
        @Mapping(target = "customerOrders", source = "destination.customerOrders"),
        @Mapping(target = "customer", source = "destination.user"),
        @Mapping(target = "processed", source = "destination.processed"),
        @Mapping(target = "total", source = "destination.total")
    })
    Orders destinationToSource(OrderEntity destination);
}