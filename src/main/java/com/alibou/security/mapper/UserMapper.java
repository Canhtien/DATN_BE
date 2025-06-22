package com.alibou.security.mapper;


import com.alibou.security.entity.User;
import com.alibou.security.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "dateOfBirth", target = "DateOfBirth")
    UserResponse toUserResponse(User user);
}
