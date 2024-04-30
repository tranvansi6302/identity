package com.project.identity.controllers;

import com.project.identity.dtos.requests.UserCreateRequest;
import com.project.identity.dtos.requests.UserUpdateRequest;
import com.project.identity.dtos.responses.ApiResponse;
import com.project.identity.dtos.responses.UserResponse;
import com.project.identity.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor

public class UserController {
    private final IUserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String id) {
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(id))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserCreateRequest request) {
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .code(201)
                .result(userService.createUser(request))
                .message("User registered successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable String id,
                                                        @RequestBody UserUpdateRequest request) {
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, request))
                .message("User updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable String id) {
        ApiResponse<?> response = ApiResponse.builder()
                .message("User deleted successfully")
                .build();
        userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe() {
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .result(userService.getMe())
                .build();
        return ResponseEntity.ok(response);
    }
}
