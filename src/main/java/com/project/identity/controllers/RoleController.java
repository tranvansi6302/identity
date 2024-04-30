package com.project.identity.controllers;

import com.project.identity.dtos.requests.PermissionRequest;
import com.project.identity.dtos.requests.RoleRequest;
import com.project.identity.dtos.responses.ApiResponse;
import com.project.identity.dtos.responses.PermissionResponse;
import com.project.identity.dtos.responses.RoleResponse;
import com.project.identity.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        return ResponseEntity.ok(ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(ApiResponse.<RoleResponse>builder()
                .message("Role created successfully")
                .result(roleService.createRole(request))
                .build());
    }

}
