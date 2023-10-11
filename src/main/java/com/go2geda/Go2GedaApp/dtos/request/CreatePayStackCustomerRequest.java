package com.go2geda.Go2GedaApp.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePayStackCustomerRequest {
    private String email;
    private String first_name;
    private String last_name;
    private String phone;
}
