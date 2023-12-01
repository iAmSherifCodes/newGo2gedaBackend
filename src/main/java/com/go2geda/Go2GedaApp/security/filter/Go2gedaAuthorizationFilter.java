package com.go2geda.Go2GedaApp.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.go2geda.Go2GedaApp.configs.AppConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.go2geda.Go2GedaApp.utils.AppUtils.APP_NAME;
import static com.go2geda.Go2GedaApp.utils.AppUtils.getPublicPaths;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j @AllArgsConstructor @NoArgsConstructor(force = true)
public class Go2gedaAuthorizationFilter extends OncePerRequestFilter {
    private final AppConfig appConfig;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (getPublicPaths().contains(request.getServletPath())) filterChain.doFilter(request, response);
        else{
            String auth = request.getHeader(AUTHORIZATION);
            log.info("{{{{}}}}}}++++++=====>>>>", auth);
            String token = auth.substring("Bearer ".length());

            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(appConfig.getJwtAlgorithmSecret()))
                    .withIssuer(APP_NAME)
                    .withClaimPresence(appConfig.getJwtAccessTokenClaim())
                    .build();

            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim(appConfig.getJwtAccessTokenClaim())
                    .asList(SimpleGrantedAuthority.class);

            log.info("authorities->{}", authorities);
            Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        }
    }
}
