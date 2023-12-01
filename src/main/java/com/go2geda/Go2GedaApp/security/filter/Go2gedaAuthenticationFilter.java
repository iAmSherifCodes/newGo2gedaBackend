package com.go2geda.Go2GedaApp.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.go2geda.Go2GedaApp.dtos.request.LoginRequest;
import com.go2geda.Go2GedaApp.dtos.response.ApiResponse;
import com.go2geda.Go2GedaApp.utils.JWUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@Slf4j
public class Go2gedaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final JWUtils jwUtils;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            InputStream inputStream = request.getInputStream();
            LoginRequest loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);
            String username = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticationResult = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        Collection<?extends GrantedAuthority> userAuthorities = authResult.getAuthorities();
        List<? extends GrantedAuthority> authorities = new ArrayList<>(userAuthorities);
        var roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        String token = jwUtils.generateAccessToken(roles);
        var apiResponse = ApiResponse.builder().data(token).build();
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }

}
