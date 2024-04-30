package com.project.identity.dtos.requests;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
    private String name;
    private String password;
    private LocalDate dateOfBirth;
    List<String> roles;
}
