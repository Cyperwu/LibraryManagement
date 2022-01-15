package com.cyper.library.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class ServiceFailureException extends RuntimeException {

    @NonNull
    private String message = "";

    private Integer statusCode = 400;

    public ServiceFailureException(String message) {
        super(message);
    }

}
