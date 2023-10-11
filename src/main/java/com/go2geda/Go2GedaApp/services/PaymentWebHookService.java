package com.go2geda.Go2GedaApp.services;

import java.util.Map;

public interface PaymentWebHookService {
    Map<String, Object> findWebhookDetails(Map<String, Object> webHookNotification);
}
