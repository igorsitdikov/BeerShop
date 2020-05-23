package com.gp.beershop.mapper;

import com.gp.beershop.dto.User;
import com.gp.beershop.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity sourceToDestination(User source);

    @Mapping(target = "password", ignore = true)
    User destinationToSource(UserEntity destination);
}
