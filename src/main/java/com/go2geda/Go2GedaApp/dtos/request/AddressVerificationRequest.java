package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressVerificationRequest {
    private String localGovernment;
    private String state;
    private String homeAddress;
}
