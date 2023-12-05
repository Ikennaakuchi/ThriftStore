package com.ikennaprojects.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ExceptionResponse {
    private String errorMessage;
    private int errorCode;
    private Date timestamp;
}
