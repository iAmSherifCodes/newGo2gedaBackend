package com.go2geda.Go2GedaApp.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter @Getter
public class ErrorResponse {
    private String message;
    private Date timeStamp;
    private Integer statusCode;
}
