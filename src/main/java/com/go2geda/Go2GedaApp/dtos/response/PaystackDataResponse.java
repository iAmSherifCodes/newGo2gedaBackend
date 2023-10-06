package com.go2geda.Go2GedaApp.dtos.response;

import java.math.BigDecimal;

public class PaystackDataResponse {
    private String id;
    private String domain;
    private String status;
    private String reference;
    private BigDecimal amount;
    private String message;
    private String gateway_response;
    private String paid_at;
    private String created_at;
    private String channel;
    private String currency;
    private String ip_address;
    private PaystackMetaDataResponse metadata;
    private PaystackLogResponse log;
    private String fees;
    private String fees_split;
    private PaystackAuthorizationResponse authorization;
    private PayStackCustomerResponse customer;
    private String plan;
    private String paidAt;
    private String createdAt;
    private String transaction_date;
    private  BigDecimal requested_amount;
}
