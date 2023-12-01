package com.go2geda.Go2GedaApp.utils;

import java.time.Instant;
import java.util.List;

import static com.go2geda.Go2GedaApp.utils.AppUtils.APP_NAME;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.go2geda.Go2GedaApp.configs.AppConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Slf4j
@AllArgsConstructor
@Component
public class JWUtils {
    private final AppConfig appConfig;

    public String generateVerificationToken(String email){
        String token = JWT.create()
                .withClaim(appConfig.getJwtVerifyClaim(), email)
                .withIssuer(APP_NAME)
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(Algorithm.HMAC512(appConfig.getJwtAlgorithmSecret()));
        return token;
    }

    public String generateAccessToken(List<String> authorities){
        log.info("claim:: {}", appConfig.getJwtAccessTokenClaim());
        String token = JWT.create()
                .withClaim(appConfig.getJwtAccessTokenClaim(), authorities)
                .withIssuer(APP_NAME)
                .withExpiresAt(Instant.now().plusSeconds(3600*24))
                .sign(Algorithm.HMAC512(appConfig.getJwtAlgorithmSecret()));
        return token;
    }
}
