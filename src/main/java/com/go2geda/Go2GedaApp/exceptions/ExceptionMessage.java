package com.go2geda.Go2GedaApp.exceptions;

public enum ExceptionMessage {
    USER_NOT_FOUND("USER NOT FOUND"),
    AUTHENTICATION_NOT_SUPPORTED("AUTHENTICATION_NOT_SUPPORTED"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS"),
    EMAIL_ALREADY_EXIST("EMAIL_ALREADY_EXIST");

    ExceptionMessage(String message){
    }
}
