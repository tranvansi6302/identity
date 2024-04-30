package com.project.identity.services;

import com.project.identity.dtos.requests.RoleRequest;
import com.project.identity.dtos.responses.RoleResponse;

import java.util.List;

public interface IRoleService {
     RoleResponse createRole(RoleRequest request);

     List<RoleResponse> getAllRoles();
}
