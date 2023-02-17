package com.cinema.cinema.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {

    private Timestamp timestamp;
    private String details;
    private Map<String, String> validationErrors;

    public ErrorResponse(String details) {
        this.details = details;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.validationErrors = new HashMap<>();
    }

}
