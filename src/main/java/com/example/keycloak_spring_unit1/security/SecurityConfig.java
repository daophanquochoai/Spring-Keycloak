package com.example.keycloak_spring_unit1.security;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.authorization.spi.ConfigurationResolver;
import org.keycloak.adapters.authorization.spi.HttpRequest;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(t -> t.disable())
                .addFilterAfter(servletPolicyEnforcerFilter(), BearerTokenAuthenticationFilter.class)
                .sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    private ServletPolicyEnforcerFilter servletPolicyEnforcerFilter() {
        return new ServletPolicyEnforcerFilter(new ConfigurationResolver() {
            @Override
            public PolicyEnforcerConfig resolve(HttpRequest httpRequest) {
                try {
                    return JsonSerialization.readValue(
                            getClass().getResourceAsStream("/policy-enforcer.json"),
                            PolicyEnforcerConfig.class
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        String jwkSetUri = "http://localhost:8080/realms/Spring-Be/protocol/openid-connect/certs";
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
}
