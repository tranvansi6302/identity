package com.project.identity.mappers;

import com.project.identity.dtos.requests.PermissionRequest;
import com.project.identity.dtos.requests.RoleRequest;
import com.project.identity.dtos.responses.PermissionResponse;
import com.project.identity.dtos.responses.RoleResponse;
import com.project.identity.entities.Permission;
import com.project.identity.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")

public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true) // không map permissions, chúng ta tự map
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
