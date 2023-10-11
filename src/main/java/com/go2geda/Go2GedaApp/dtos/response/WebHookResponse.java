package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
@Setter
@Getter
public class WebHookResponse  extends  BaseResponse{
    private Map<String, Object> response;

    public WebHookResponse(boolean isSuccessful, int responseCode, String message, LocalDateTime timeStamp, Map<String, Object> response) {
        super(isSuccessful, responseCode, message, timeStamp);
        this.response = response;
    }

}
