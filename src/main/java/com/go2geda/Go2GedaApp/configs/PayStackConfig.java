package com.go2geda.Go2GedaApp.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
public class PayStackConfig {
    @Value("payStackPublicKey")
    private String publicKey;
    @Value("payStackSecretKey")
    private String secretKey;
    @Value("CustomerUrl")
    private String createCustomerUrl;
    @Value("CustomerValidationUrl")
    private  String customerValidationUrl;
    @Value("CustomerVirtualUrl")
   private String CreateVirtualAccountUrl;
}
