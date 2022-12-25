package com.demo.broker.mapper;

import com.demo.broker.dto.UserDto;
import com.demo.broker.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
}
