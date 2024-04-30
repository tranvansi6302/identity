package com.project.identity.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "VALIDATION_USERNAME_REQUIRED")
    private String username;

    @NotBlank(message = "VALIDATION_PASSWORD_REQUIRED")
    private String password;
}
