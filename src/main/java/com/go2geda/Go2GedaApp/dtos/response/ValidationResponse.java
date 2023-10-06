package com.go2geda.Go2GedaApp.dtos.response;

import java.time.LocalDateTime;

public class ValidationResponse extends BaseResponse{
    PayStackValidationResponse response;

    public ValidationResponse(boolean isSuccessful, int responseCode, String message, LocalDateTime timeStamp,PayStackValidationResponse response) {
        super(isSuccessful, responseCode, message, timeStamp);
        this.response = response;
    }
}
