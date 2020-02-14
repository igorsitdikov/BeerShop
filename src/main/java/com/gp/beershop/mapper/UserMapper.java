package com.gp.beershop.mapper;

import com.gp.beershop.dto.Customer;
import com.gp.beershop.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity sourceToDestination(Customer source);

    Customer destinationToSource(UserEntity destination);
}