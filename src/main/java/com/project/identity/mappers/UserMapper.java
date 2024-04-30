package com.project.identity.mappers;

import com.project.identity.dtos.requests.UserCreateRequest;
import com.project.identity.dtos.requests.UserUpdateRequest;
import com.project.identity.dtos.responses.UserResponse;
import com.project.identity.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")

public interface UserMapper {
    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true) // User -> Set<Role>, request -> List<String> -> ignore
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
