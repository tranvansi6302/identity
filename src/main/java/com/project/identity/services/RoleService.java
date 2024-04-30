package com.project.identity.services;

import com.project.identity.dtos.requests.RoleRequest;
import com.project.identity.dtos.responses.RoleResponse;
import com.project.identity.entities.Permission;
import com.project.identity.entities.Role;
import com.project.identity.mappers.RoleMapper;
import com.project.identity.repositories.PermissionRepository;
import com.project.identity.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;


    @Override
    public RoleResponse createRole(RoleRequest request) {
        Role role = roleMapper.toRole(request);
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));

    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }
}
