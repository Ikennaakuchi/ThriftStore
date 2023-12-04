package com.ikennaprojects.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Map<String, String> error = new HashMap<>();

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OutOfStockException.class)
    public Map<String, String> handleOutOfStockException(OutOfStockException ex){
        error.put("error-code", "404");
        error.put("error message", ex.getMessage());
        error.put("date", String.valueOf(new Date()));
        return error;
    }
}
