package com.ainterview.dto;

import java.util.Date;

public record ErrorResponse (
    int status,
    String error,
    String message,
    String path,
    Date timestamp
) {
    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(status, error, message, path, new Date());
    }
}
