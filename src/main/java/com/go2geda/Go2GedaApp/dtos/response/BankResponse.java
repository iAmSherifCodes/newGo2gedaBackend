package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Getter;
import lombok.Setter;
import org.cloudinary.json.JSONObject;

import java.time.LocalDateTime;
@Getter
@Setter
public class BankResponse  extends BaseResponse{

private JSONObject getBankCodes;

    public BankResponse(boolean isSuccessful, int responseCode, String message, LocalDateTime timeStamp,JSONObject getBankCodes) {
        super(isSuccessful, responseCode, message, timeStamp);
        this.getBankCodes = getBankCodes;


    }

}
