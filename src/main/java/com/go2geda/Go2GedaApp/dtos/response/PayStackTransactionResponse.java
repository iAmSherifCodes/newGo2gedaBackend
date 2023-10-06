package com.go2geda.Go2GedaApp.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class PayStackTransactionResponse {
    private BigDecimal amount;
}
