package com.go2geda.Go2GedaApp.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateVirtualAccountRequest {
    private String customer;
    private String email;
    private String preferred_bank="wema-bank";
//    private String bank = "wema-bank";
    //    @Value("${paystack_subaccount}")
//   private String subaccount ="ACCT_5y8vsij364itc0g";
//    private String split_code ="ACCT_5y8vsij364itc0g";
}
