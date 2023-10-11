package com.go2geda.Go2GedaApp.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VirtualAccountDataResponse {
    private VirtualAccountBankResponse bank;
    private String account_name;
    private String account_number;
    private boolean assigned;
    private String currency;
    private Object metadata;
    private boolean active;
    private Long id;
    private String created_at;
    private String updated_at;
    private VirtualAccountAssignmentResponse assignment;
    private PayStackCustomerResponse customer;



}
