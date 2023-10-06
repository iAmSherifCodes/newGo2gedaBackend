package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class VirtualBaseResponse extends BaseResponse{
    private VirtualAccountResponse response;

    public VirtualBaseResponse(boolean isSuccessful, int responseCode, String message, LocalDateTime timeStamp, VirtualAccountResponse response) {
        super(isSuccessful, responseCode, message, timeStamp);
        this.response = response;
    }
}
