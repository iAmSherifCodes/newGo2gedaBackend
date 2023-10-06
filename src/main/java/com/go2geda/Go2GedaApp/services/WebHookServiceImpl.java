package com.go2geda.Go2GedaApp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class WebHookServiceImpl implements PaymentWebHookService{

    private PaymentService paymentService;
    @Override
    public Map<String, Object> findWebhookDetails(Map<String, Object> webHookNotification) {
        if (Objects.equals(webHookNotification.get("event"), "charge.success")){
            paymentService.addFundToWallet((Long) webHookNotification.get("id"), (BigDecimal) webHookNotification.get("amount"));
            return webHookNotification;
        }
        return webHookNotification;
    }
}
