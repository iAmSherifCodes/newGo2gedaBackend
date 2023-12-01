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
import static com.go2geda.Go2GedaApp.utils.AppUtils.*;

@AllArgsConstructor
@Configuration
public class BuildEmailRequest {
    private AppConfig appConfig;
    private final AppUtils appUtils;
    public EmailSenderRequest buildEmailRequest(User savedUser){
        EmailSenderRequest request =new EmailSenderRequest();
        List<MailInfo> recipients = new ArrayList<>();
        MailInfo recipient = new MailInfo(savedUser.getBasicInformation().getFirstName() + SPACE + savedUser.getBasicInformation().getLastName(), savedUser.getBasicInformation().getEmail());
        recipients.add(recipient);
        request.setTo(recipients);
        request.setSubject(WELCOME_MAIL_SUBJECT);
//        String activationLink =
//                generateActivationLink(appConfig.getBaseUrl(), savedUser.getBasicInformation().getEmail());
        String emailTemplate = getMailTemplate();
        String mailContent = String.format(emailTemplate, savedUser.getBasicInformation().getFirstName());//, activationLink);
        request.setHtmlContent(mailContent);
        return request;
    }
    public EmailSenderRequest buildEmailRequestCommuter(User savedUser){
        EmailSenderRequest request =new EmailSenderRequest();
        List<MailInfo> recipients = new ArrayList<>();
        MailInfo recipient = new MailInfo(savedUser.getBasicInformation().getFirstName() + SPACE + savedUser.getBasicInformation().getLastName(), savedUser.getBasicInformation().getEmail());
        recipients.add(recipient);
        request.setTo(recipients);
        request.setSubject(WELCOME_MAIL_SUBJECT);
        String activationLink = appUtils.generateActivationLink(appConfig.getBaseUrl(), savedUser.getBasicInformation().getEmail());
        String emailTemplate = getMailTemplateCommuter();
        String mailContent = String.format(emailTemplate, savedUser.getBasicInformation().getFirstName(), activationLink);
        request.setHtmlContent(mailContent);
        return request;
    }
}

