package com.go2geda.Go2GedaApp.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Setter
@Getter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal balance;
    private Long userId;
    private String walletName;
    private String walletBank;
    private String walletPin;
    private String paymentLink;
    private String walletBVN;
    private LocalDate dateCreated;
}

