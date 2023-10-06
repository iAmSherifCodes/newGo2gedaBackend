package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaystackAuthorizationResponse {
    private String authorization_code;
    private String bin;
    private String last4;
    private String exp_month;
    private String exp_year;
    private String channel;
    private String card_type;
    private String bank;
    private String country_code;
    private String brand;
    private Boolean reusable;
    private String signature;
    private String account_name;
    private String receiver_bank_account_number;
    private String receiver_bank;
}
