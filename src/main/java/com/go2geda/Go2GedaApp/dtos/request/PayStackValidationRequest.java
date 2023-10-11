package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayStackValidationRequest {
    private String country;
    private String  account_number;
    private String type="bank_account";
    private String bvn;
    private String bank_code;
    private String  firstName;
    private String lastName;
}
