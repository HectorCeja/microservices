package com.ceja.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JWTAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity httpSecurity) {
        return httpSecurity.authorizeExchange()
                .pathMatchers("/api/security/oauth/**").permitAll()
                .pathMatchers(HttpMethod.GET,
                        "/api/products/list",
                        "/api/items/list",
                        "/api/items/{id}/detail/quantity/{quantity}",
                        "/api/users/users").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/users/users/{id}", "/api/products/{id}").hasAnyRole("ADMIN", "USER")
                .pathMatchers("/api/products/**", "/api/users/users/**", "/api/items/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and().addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION) //here is the filter for the token
                .csrf().disable()
                .build();
    }

}
