package com.cinema.cinema.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String path;
    private int code;
    private HttpStatus status;
    private String details;

}
