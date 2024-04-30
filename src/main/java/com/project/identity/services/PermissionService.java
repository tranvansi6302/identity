package com.project.identity.services;

import com.project.identity.dtos.requests.PermissionRequest;
import com.project.identity.dtos.responses.PermissionResponse;
import com.project.identity.entities.Permission;
import com.project.identity.mappers.PermissionMapper;
import com.project.identity.repositories.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public void deletePermission(String permission) {
        permissionRepository.deleteById(permission);
    }
}
