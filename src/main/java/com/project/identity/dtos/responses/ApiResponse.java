package com.project.identity.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.identity.exceptions.ErrorCode;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON response
public class ApiResponse<T> {
    @Builder.Default
    private int code= 1000;
    private String message;
    private T result;
}
