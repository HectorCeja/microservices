package com.ceja.gateway.filters.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Configuration> {

   private final Logger logger = LoggerFactory.getLogger(this.getClass());

   public CustomGatewayFilterFactory() {
      super(Configuration.class);
   }

   @Override
   public GatewayFilter apply(Configuration config) {
      return (exchange, chain) -> {
         logger.info("Executing pre gateway filter " + config.message);
         return chain.filter(exchange).then(Mono.fromRunnable(()-> {
            Optional.ofNullable(config.cookieValue).ifPresent(cookie -> {
               exchange.getResponse().addCookie(ResponseCookie.from(config.cookieValue, cookie).build());
            });
            logger.info("Executing post gateway filter " + config.message);
         }));
      };
   }

   @Override
   public String name() {
      return "CookiesFilter";
   }

   public static class Configuration {

      private String message;

      private String cookieValue;

      private String cookieKey;

      public String getMessage() {
         return message;
      }

      public void setMessage(String message) {
         this.message = message;
      }

      public String getCookieValue() {
         return cookieValue;
      }

      public void setCookieValue(String cookieValue) {
         this.cookieValue = cookieValue;
      }

      public String getCookieKey() {
         return cookieKey;
      }

      public void setCookieKey(String cookieKey) {
         this.cookieKey = cookieKey;
      }
   }
}
