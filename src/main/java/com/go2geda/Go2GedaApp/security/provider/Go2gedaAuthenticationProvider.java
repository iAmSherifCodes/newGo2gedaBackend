package com.go2geda.Go2GedaApp.security.provider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static com.go2geda.Go2GedaApp.exceptions.ExceptionMessage.INVALID_CREDENTIALS;

@AllArgsConstructor @Slf4j @Component
public class Go2gedaAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        UserDetails user = userDetailsService.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();
        boolean isPasswordMatch = passwordEncoder.matches(password, user.getPassword());

        if(isPasswordMatch){
            Collection<? extends GrantedAuthority> authoritiesResult = user.getAuthorities();
            Authentication authenticationResult = new UsernamePasswordAuthenticationToken(username, password, authoritiesResult);
            return authenticationResult;
        }
        throw new BadCredentialsException(INVALID_CREDENTIALS.name());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
