package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDetailsVerificationRequest {
    private String bankVerificationNUmber;
    private String accountNUmber;
    private String bankName;
}
