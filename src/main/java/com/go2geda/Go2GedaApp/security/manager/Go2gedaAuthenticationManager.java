package com.go2geda.Go2GedaApp.security.manager;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static com.go2geda.Go2GedaApp.exceptions.ExceptionMessage.AUTHENTICATION_NOT_SUPPORTED;

@AllArgsConstructor
@Component
public class Go2gedaAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authenticationProvider.supports(authentication.getClass())){
            Authentication authenticationResult = authenticationProvider.authenticate(authentication);
            return authenticationResult;
        }
        throw new AuthenticationException(AUTHENTICATION_NOT_SUPPORTED.name()) {
        };
    }
}
