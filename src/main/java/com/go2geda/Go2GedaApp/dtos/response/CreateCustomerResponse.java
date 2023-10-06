package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class CreateCustomerResponse extends BaseResponse{

    private CreatePayStackCustomerResponse response;

    public CreateCustomerResponse(boolean isSuccessful, int responseCode, String message, LocalDateTime timeStamp, CreatePayStackCustomerResponse response) {
        super(isSuccessful, responseCode, message, timeStamp);

        this.response =response;
    }
}
