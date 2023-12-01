package com.go2geda.Go2GedaApp.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.*;


@Getter
@Configuration
public class AppConfig {
    public final static  String SPACE = " ";
    public static final String ACTIVATE_ACCOUNT_PATH = "/user/activate?code=";
    public final static String WELCOME_MAIL_SUBJECT = "Welcome to Go2Geda Platform";

    @Value("${mail.api.key}")
    private String mailApiKey;

    @Value("${mail.brevo.address}")
    private String brevoMailAddress;

    @Value("${jwt.secret}")
    private String jwtAlgorithmSecret;

    @Value("${jwt.verify.claim}")
    private String jwtVerifyClaim;

    @Value("${jwt.access.token.claim}")
    private String jwtAccessTokenClaim;

    @Value("${app.base.url}")
    private String baseUrl;

    @Value("${cloud.api.key}")
    private String cloudApiKey;

    @Value("${cloud.secret.key}")
    private String cloudSecret;

    @Value("${name.cloudinary.cloud}")
    private String cloudName;


    public String getJwtAccessTokenClaim() {
        return jwtAccessTokenClaim;
    }

    public String getDriverLicenseFrontPictureTest() {
        return driverLicenseFrontPictureTest;
    }

    public String getDriverLicenseBackPictureTest() {
        return driverLicenseBackPictureTest;
    }

    @Value("${front.license}")
    private String driverLicenseFrontPictureTest;

    @Value("${back.license}")
    private String driverLicenseBackPictureTest;


    public String getCloudApiKey() {
        return cloudApiKey;
    }

    public String getCloudSecret() {
        return cloudSecret;
    }

    public Object getCloudName() {
        return cloudName;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods(POST.name(), GET.name(), OPTIONS.name(), PUT.name())
                        .allowedOrigins("*");
            }
        };
    }
}
