package com.gp.beershop.mapper;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.entity.BeerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerMapper {
    BeerEntity sourceToDestination(Beer source);

    Beer destinationToSource(BeerEntity destination);
}