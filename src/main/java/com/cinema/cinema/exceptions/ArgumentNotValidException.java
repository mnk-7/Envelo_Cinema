package com.cinema.cinema.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ArgumentNotValidException extends RuntimeException {

    private Map<String, String> violations;

    public ArgumentNotValidException(String message) {
        super(message);
        this.violations = new HashMap<>();
    }

    public ArgumentNotValidException(String message, Map<String, String> violations) {
        super(message);
        this.violations = violations;
    }

}
