package com.go2geda.Go2GedaApp.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PayStackSubscriptionResponse {
    private Boolean status;
    private String message;
    private PaystackDataResponse data;
}
