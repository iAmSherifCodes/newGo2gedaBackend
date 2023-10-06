package com.go2geda.Go2GedaApp.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
public class PayStackConfig {
    @Value("k_test_58c3d2b2311ff870ffa747a588dcca3c19380303")
    private String publicKey;
    @Value("sk_test_f54df64c4ca66079dddc461a3884ec787c729acd")
    private String secretKey;
    @Value("https://api.paystack.co/customer")
    private String createCustomerUrl;
    @Value("https://api.paystack.co/customer/")
    private  String customerValidationUrl;
    @Value("https://api.paystack.co/dedicated_account")
   private String CreateVirtualAccountUrl;
}
