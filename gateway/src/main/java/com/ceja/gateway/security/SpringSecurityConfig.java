package com.ceja.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity httpSecurity) {
        return httpSecurity.authorizeExchange()
                .pathMatchers("/api/security/oauth/**").permitAll()
                .pathMatchers(HttpMethod.GET,
                        "/api/products/list",
                        "/api/items/list",
                        "/api/products/{id}",
                        "/api/items/{id}/detail/quantity/{quantity}",
                        "/api/users/users").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/users/users/{id}").hasAnyRole("ADMIN", "USER")
                .pathMatchers("/api/products/**", "/api/users/users/**", "/api/items/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and().csrf().disable()
                .build();
    }

}
