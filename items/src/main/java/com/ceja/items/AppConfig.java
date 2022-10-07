package com.ceja.items;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

   @Bean("rest-client")
   @LoadBalanced
   public RestTemplate registerRestTemplate() {
      return new RestTemplate();
   }

   /*@Bean
   public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
      return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
              .circuitBreakerConfig(CircuitBreakerConfig.custom()
                      .slidingWindowSize(10) // NUMERO PERMITIDO DE LLAMADAS TOTALES
                      .failureRateThreshold(50) // PORCENTAJE PARA ENTRAR EN CIRCUITO ABIERTO
                      .waitDurationInOpenState(Duration.ofSeconds(10)) //DURACION EN CIRCUITO SEMI ABIERTO
                      .permittedNumberOfCallsInHalfOpenState(5) // NUMERO DE LLAMAS EN CIRCUITO SEMI ABIERTO
                      .slowCallRateThreshold(50) //PORCENTAJE DE LLAMADAS LENTAS PARA ENTRAR EN CIRCUITO ABIERTO
                      .slowCallDurationThreshold(Duration.ofSeconds(2L)) //DURACION LIMITE PARA LLAMADAS LENTAS
                      .build()
              ).timeLimiterConfig(TimeLimiterConfig.custom()
                      .timeoutDuration(Duration.ofSeconds(3L)) //TIMEOUT MAXIMO
                      .build())
              .build());
   }*/

}
