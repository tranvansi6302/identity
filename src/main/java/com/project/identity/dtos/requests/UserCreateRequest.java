package com.project.identity.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "VALIDATION_NAME_REQUIRED" )
    @Size(min = 3, max = 50, message = "VALIDATION_NAME_LENGTH")
    private String name;

    @NotBlank(message = "VALIDATION_USERNAME_REQUIRED")
    private String username;

    @NotBlank(message = "VALIDATION_PASSWORD_REQUIRED")
    @Size(min = 6, max = 50, message = "VALIDATION_PASSWORD_LENGTH")
    private String password;

    private LocalDate dateOfBirth;
}
