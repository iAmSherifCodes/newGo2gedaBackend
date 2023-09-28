package com.go2geda.Go2GedaApp.utils;

import com.go2geda.Go2GedaApp.configs.AppConfig;
import com.go2geda.Go2GedaApp.data.models.User;
import com.go2geda.Go2GedaApp.dtos.request.EmailSenderRequest;
import com.go2geda.Go2GedaApp.dtos.request.MailInfo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static com.go2geda.Go2GedaApp.configs.AppConfig.SPACE;
import static com.go2geda.Go2GedaApp.configs.AppConfig.WELCOME_MAIL_SUBJECT;
import static com.go2geda.Go2GedaApp.utils.AppUtils.generateActivationLink;
import static com.go2geda.Go2GedaApp.utils.AppUtils.getMailTemplate;

@AllArgsConstructor
@Configuration
public class BuildEmailRequest {
    private AppConfig appConfig;

    public EmailSenderRequest buildEmailRequest(User savedUser){
        EmailSenderRequest request =new EmailSenderRequest();
        List<MailInfo> recipients = new ArrayList<>();
        MailInfo recipient = new MailInfo(savedUser.getBasicInformation().getFirstName() + SPACE + savedUser.getBasicInformation().getLastName(), savedUser.getBasicInformation().getEmail());
        recipients.add(recipient);
        request.setTo(recipients);
        request.setSubject(WELCOME_MAIL_SUBJECT);
        String activationLink =
                generateActivationLink(appConfig.getBaseUrl(), savedUser.getBasicInformation().getEmail());
        String emailTemplate = getMailTemplate();
        String mailContent = String.format(emailTemplate, savedUser.getBasicInformation().getFirstName(), activationLink);
        request.setHtmlContent(mailContent);
        return request;
    }
}

