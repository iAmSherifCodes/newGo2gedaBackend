package com.go2geda.Go2GedaApp.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateWalletRequest {
    private Long userId;
    private String walletBvn;
    private String walletPin;
    private String accountNumber;
    private String bankCode;
    private String bank;
}
