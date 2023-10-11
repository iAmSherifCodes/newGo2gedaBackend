package com.go2geda.Go2GedaApp.dtos.response;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class PayStackCustomerResponse {
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String customer_code;
    private String phone;
    private String risk_action;


}
