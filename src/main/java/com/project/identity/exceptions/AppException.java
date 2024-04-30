package com.project.identity.exceptions;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppException extends RuntimeException{
    private ErrorCode errorCode;
}
