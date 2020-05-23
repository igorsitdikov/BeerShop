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
        @Mapping(target = "user", source = "source.user"),
        @Mapping(target = "processed", source = "source.processed"),
        @Mapping(target = "canceled", source = "source.canceled"),
        @Mapping(target = "total", source = "source.total")
    })
    OrderEntity sourceToDestination(Orders source);

    @Mappings({
        @Mapping(target = "id", source = "destination.id"),
        @Mapping(target = "customerOrders", source = "destination.customerOrders"),
        @Mapping(target = "user", source = "destination.user"),
        @Mapping(target = "processed", source = "destination.processed"),
        @Mapping(target = "canceled", source = "destination.canceled"),
        @Mapping(target = "total", source = "destination.total"),
        @Mapping(target = "user.password", ignore = true)
    })
    Orders destinationToSource(OrderEntity destination);
}
