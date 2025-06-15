package com.user.imvs.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ErrorResponse {
    private String errorCode;
    private String message;
    public ErrorResponse(String errorCode, String message) {
        this.message = message;
        this.errorCode=errorCode;
    }
}
