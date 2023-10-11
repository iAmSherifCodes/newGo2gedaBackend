package com.go2geda.Go2GedaApp.dtos.response;

import com.go2geda.Go2GedaApp.data.models.Wallet;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Setter
@Getter
public class WalletBaseResponse extends BaseResponse {
    private Wallet wallet;
    public WalletBaseResponse(boolean isSuccessful, int responseCode, String message, LocalDateTime timeStamp, Wallet wallet) {
        super(isSuccessful, responseCode, message, timeStamp);
        this.wallet = wallet;
    }

}
