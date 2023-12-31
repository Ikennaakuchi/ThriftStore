package com.ikennaprojects.orderservice.exception;

import com.ikennaprojects.orderservice.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.concurrent.TimeoutException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OutOfStockException.class)
    public ExceptionResponse handleOutOfStockException(OutOfStockException ex) {

        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(new Date());
        response.setErrorMessage(ex.getMessage());
        response.setErrorCode(400);

        log.info("An error will be handled here {}", response);
        return response;
    }

    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ExceptionHandler(TimeoutException.class)
    public ExceptionResponse handleTimeOutException(TimeoutException ex) {

        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(new Date());
        response.setErrorMessage(ex.getMessage());
        response.setErrorCode(HttpStatus.REQUEST_TIMEOUT.value());

        log.info("An error will be handled here {}", response);
        return response;
    }
}
