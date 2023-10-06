package com.go2geda.Go2GedaApp.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePayStackCustomerDataResponse {
    private List<PayStackTransactionResponse> transactions;
    private List<PayStackSubscriptionResponse> subscriptions;
    private List<PaystackAuthorizationResponse> authorizations;
    private String email;
    private String first_name;
    private String last_name;
    private String phone;
    private String integration;
    private String domain;
    private PaystackMetaDataResponse metadata;
    private String customer_code;
    private String risk_action;
    private String id;
    private String createdAt;
    private String updatedAt;
    private String identified;
    private String identifications;
}
