package com.go2geda.Go2GedaApp.utils;

import java.time.Instant;
import java.util.List;

import static com.go2geda.Go2GedaApp.utils.AppUtils.APP_NAME;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWUtils {
    public static String generateVerificationToken(String email){
        String token = JWT.create()
                .withClaim("user", email)
                .withIssuer(APP_NAME)
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(Algorithm.HMAC512("null_value"));
        return token;
    }

    public static String generateAccessToken(List<String> authorities){
        String token = JWT.create()
                .withClaim("roles", authorities)
                .withIssuer(APP_NAME)
                .withExpiresAt(Instant.now().plusSeconds(3600*24))
                .sign(Algorithm.HMAC512("null_value"));
        return token;
    }
}
