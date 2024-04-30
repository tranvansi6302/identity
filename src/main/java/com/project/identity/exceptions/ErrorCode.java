package com.project.identity.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    UNCAUGHT_EXCEPTION(9999, "An error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USER_EXISTS(1002, "User already exists", HttpStatus.BAD_REQUEST),
    USERNAME_OR_PASSWORD_INCORRECT(1003, "Username or password is incorrect", HttpStatus.BAD_REQUEST),
    VALIDATION_INVALID_KEY(1004, "Invalid key", HttpStatus.BAD_REQUEST),
    VALIDATION_USERNAME_REQUIRED(1005, "Username is required", HttpStatus.BAD_REQUEST),
    VALIDATION_PASSWORD_REQUIRED(1006, "Password is required", HttpStatus.BAD_REQUEST),
    VALIDATION_NAME_REQUIRED(1007, "Name is required", HttpStatus.BAD_REQUEST),
    VALIDATION_NAME_LENGTH(1008, "Name must be between 3 and 50 characters", HttpStatus.BAD_REQUEST),
    VALIDATION_PASSWORD_LENGTH(1009, "Password must be between 6 and 50 characters", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1010, "Unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(1011, "Permission denied", HttpStatus.FORBIDDEN);
    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
