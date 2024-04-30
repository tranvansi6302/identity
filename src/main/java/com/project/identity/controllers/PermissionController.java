package com.project.identity.controllers;

import com.project.identity.dtos.requests.PermissionRequest;
import com.project.identity.dtos.responses.ApiResponse;
import com.project.identity.dtos.responses.PermissionResponse;
import com.project.identity.entities.Permission;
import com.project.identity.services.IPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final IPermissionService permissionService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllPermissions() {
        return ResponseEntity.ok(ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermissions())
                .build());
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(@RequestBody PermissionRequest request) {
        return ResponseEntity.ok(ApiResponse.<PermissionResponse>builder()
                .message("Permission created successfully")
                .result(permissionService.createPermission(request))
                .build());
    }

    @DeleteMapping("/{permission}")
    public ResponseEntity<ApiResponse<?>> deletePermission(@PathVariable String permission) {
        permissionService.deletePermission(permission);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Permission deleted successfully")
                .build());
    }
}
