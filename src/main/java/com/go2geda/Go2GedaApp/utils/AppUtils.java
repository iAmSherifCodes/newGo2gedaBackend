package com.go2geda.Go2GedaApp.utils;

import com.go2geda.Go2GedaApp.exceptions.Go2gedaBaseException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.go2geda.Go2GedaApp.configs.AppConfig.ACTIVATE_ACCOUNT_PATH;


@AllArgsConstructor
@Component
public class AppUtils {
    private final JWUtils jwUtils;
    public static final String APP_NAME = "Go2Geda";
    public static final String APP_EMAIL = "go2geda@mail.com";
    public static final String EMPTY_STRING = "";
    public static final String UPLOAD_SUCCESSFUL = "UPLOAD_SUCCESSFUL";
    public static final String VERIFICATION_SUCCESSFUL = "VERIFICATION_SUCCESSFUL";
    private static final String MAIL_TEMPLATE_LOCATION = "C:\\Users\\SHERIF\\IdeaProjects\\NewGo2gedaBackend\\src\\main\\resources\\templates\\emailHtml.html";
    private static final String MAIL_TEMPLATE_LOCATION_COMMUTER = "C:\\Users\\SHERIF\\IdeaProjects\\NewGo2gedaBackend\\src\\main\\resources\\templates\\commuterEmail.html";


    public static String getMailTemplate() {
        Path templateLocation = Paths.get(MAIL_TEMPLATE_LOCATION);
        try {
            List<String> fileContents = Files.readAllLines(templateLocation);
            String template = String.join(EMPTY_STRING, fileContents);
            return template;
        } catch (IOException exception) {
            throw new Go2gedaBaseException(exception.getMessage());
        }
    }
    public static String getMailTemplateCommuter() {
        Path templateLocation = Paths.get(MAIL_TEMPLATE_LOCATION_COMMUTER);
        try {
            List<String> fileContents = Files.readAllLines(templateLocation);
            String template = String.join(EMPTY_STRING, fileContents);
            return template;
        } catch (IOException exception) {
            throw new Go2gedaBaseException(exception.getMessage());
        }}
    public  String generateActivationLink(String baseUrl, String email){
        String token = jwUtils.generateVerificationToken(email);
        return baseUrl+ACTIVATE_ACCOUNT_PATH+token;
    }

    public static List<String> getPublicPaths(){
        return List.of("/api/v1/go2geda", "/", "/api/v1/go2geda/commuter/register-commuter","/api/v1/go2geda/driver/registerDriver","/login");
    }


}
