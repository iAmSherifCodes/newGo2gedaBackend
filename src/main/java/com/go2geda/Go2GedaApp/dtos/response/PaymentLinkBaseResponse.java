package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
public class PaymentLinkBaseResponse  extends  BaseResponse{
    private String paymentLink;

    public PaymentLinkBaseResponse(boolean isSuccessful, int responseCode, String message, LocalDateTime timeStamp,
                                   String paymentLink) {
        super(isSuccessful, responseCode, message, timeStamp);
            this.paymentLink =paymentLink;




    }
}
