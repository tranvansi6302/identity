package com.project.identity.mappers;

import com.project.identity.dtos.requests.PermissionRequest;
import com.project.identity.dtos.responses.PermissionResponse;
import com.project.identity.entities.Permission;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")

public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);

}
