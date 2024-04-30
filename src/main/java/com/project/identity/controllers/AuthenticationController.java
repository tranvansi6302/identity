package com.project.identity.controllers;

import com.nimbusds.jose.JOSEException;
import com.project.identity.dtos.requests.AuthenticationRequest;
import com.project.identity.dtos.requests.LogoutRequest;
import com.project.identity.dtos.requests.RefreshRequest;
import com.project.identity.dtos.requests.VerifyTokenRequest;
import com.project.identity.dtos.responses.ApiResponse;
import com.project.identity.dtos.responses.AuthenticationResponse;
import com.project.identity.dtos.responses.VerifyTokenResponse;
import com.project.identity.services.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse authenticate = authenticationService.authenticate(request);
        ApiResponse<AuthenticationResponse> response = ApiResponse.<AuthenticationResponse>builder()
                .result(authenticate)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-token")
    public ResponseEntity<ApiResponse<VerifyTokenResponse>> verifyToken(
            @RequestBody @Valid VerifyTokenRequest request) throws ParseException, JOSEException {
        VerifyTokenResponse verifyToken = authenticationService.verifyToken(request);
        ApiResponse<VerifyTokenResponse> response = ApiResponse.<VerifyTokenResponse>builder()
                .result(verifyToken)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@RequestBody @Valid RefreshRequest request) throws ParseException, JOSEException {
        AuthenticationResponse refreshToken = authenticationService.refreshToken(request);
        ApiResponse<AuthenticationResponse> response = ApiResponse.<AuthenticationResponse>builder()
                .result(refreshToken)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody @Valid LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Logged out successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
