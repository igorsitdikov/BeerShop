package com.gp.beershop.mapper;

import com.gp.beershop.dto.UserDTO;
import com.gp.beershop.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity sourceToDestination(UserDTO source);

    @Mapping(target = "password", ignore = true)
    UserDTO destinationToSource(UserEntity destination);
}