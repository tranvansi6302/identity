package com.project.identity.services;

import com.project.identity.dtos.requests.PermissionRequest;
import com.project.identity.dtos.responses.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse createPermission(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();

    void deletePermission(String permission);
}
