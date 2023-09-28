package com.go2geda.Go2GedaApp.services;

import com.go2geda.Go2GedaApp.dtos.request.EmailSenderRequest;
import com.go2geda.Go2GedaApp.dtos.response.OkResponse;

public interface MailService {
    OkResponse send(EmailSenderRequest request);
}
