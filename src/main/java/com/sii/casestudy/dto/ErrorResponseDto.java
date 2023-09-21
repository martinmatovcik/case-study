package com.sii.casestudy.dto;

import java.sql.Timestamp;
import org.springframework.http.HttpStatus;

public class ErrorResponseDto {

    private HttpStatus httpStatus;
    private String message;
    private Timestamp timestamp;

    public ErrorResponseDto(HttpStatus httpStatus, String message, Timestamp timestamp) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.timestamp = timestamp;
    }
}
