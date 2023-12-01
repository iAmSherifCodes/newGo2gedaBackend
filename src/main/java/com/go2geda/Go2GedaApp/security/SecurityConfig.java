package com.go2geda.Go2GedaApp.security;

import com.go2geda.Go2GedaApp.data.models.Role;
import com.go2geda.Go2GedaApp.security.filter.Go2gedaAuthenticationFilter;
import com.go2geda.Go2GedaApp.security.filter.Go2gedaAuthorizationFilter;
import com.go2geda.Go2GedaApp.security.manager.Go2gedaAuthenticationManager;
import com.go2geda.Go2GedaApp.utils.JWUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.go2geda.Go2GedaApp.data.models.Role.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @AllArgsConstructor
public class SecurityConfig {
    private final Go2gedaAuthenticationManager authenticationManager;
    private final JWUtils jwUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        final String[] publicEndPoints = {
                "/api/v1/go2geda", "/",
                "/api/v1/go2geda/commuter/register-commuter",
                "/api/v1/go2geda/driver/registerDriver",
        };

        final String[] commuterAndDriverSharedEndPoints = {
                "/trip/searchByPickup/{from}",
                "/trip/searchByTo/{to}",
                "/trip/searchTripBy/from={From}&to={To}",
                "/trip/viewTrip/{tripId}",
                "/trip/cancelTrip/{tripId}",
                "/trip/bookTrip",
                "/review/create",
                "/review/all",
        };
        final String[] commuterEndPoints = {
                "/api/v1/go2geda/commuter/getCommuter/{commuterId}",
                "/api/v1/go2geda/commuter/verify-address/{email}",
                "/trip/viewCommuterTrips/{commuterId}",
                "/trip/viewBookedTrip/{commuterId}",
        };
        final String[] driverEndPoints = {
                "/api/v1/go2geda/driver/verifyAddress",
                "/api/v1/go2geda/driver/verifyAccountDetails/{from}",
                "/api/v1/go2geda/driver/verifyDriverLicense/{from}",
                "/trip/trip-requests/{driverId}",
                "/trip/viewDriverTrips/{driverId}",
                "/trip/driver-Trip/{id}",
                "/trip/acceptTrip",
                "/trip/rejectTrip",
                "/trip/createTrip",
                "/trip/startTrip/{tripId}",
                "/trip/endTrip/{tripId}"
        };
        final String[] adminEndPoints = {
                "/review/create",
                "/review/all"
        };


        return httpSecurity
                .addFilterAt(new Go2gedaAuthenticationFilter(authenticationManager, jwUtils), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new Go2gedaAuthorizationFilter(),
                        Go2gedaAuthenticationFilter.class)
                .sessionManagement(customizer-> customizer.sessionCreationPolicy(STATELESS))
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(c->c.requestMatchers(POST, publicEndPoints).permitAll())
                .authorizeHttpRequests(c->c.requestMatchers("/api/v1/go2geda/commuter/getCommuter/{commuterId}").hasRole(COMMUTER.name()))

//                .authorizeHttpRequests(c->c.an(GET, ).hasAnyAuthority("COMMUTER"))
                .authorizeHttpRequests(c->c.requestMatchers(driverEndPoints).hasRole(DRIVER.name()))
                .authorizeHttpRequests(c->c.requestMatchers(commuterAndDriverSharedEndPoints).hasAnyRole(DRIVER.name(), COMMUTER.name()))
                .authorizeHttpRequests(c->c.requestMatchers(commuterEndPoints).hasRole(COMMUTER.name()))
                .authorizeHttpRequests(c->c.requestMatchers(adminEndPoints).hasAnyAuthority(ADMIN.name()))
                .build();
    }
}
