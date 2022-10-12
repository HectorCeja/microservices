package com.ceja.oauth.config.event;

import ceja.commons.models.entities.User;
import com.ceja.oauth.services.IUserService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        //only validate user credentials, ignoring app credentials
        if(authentication.getDetails() instanceof WebAuthenticationDetails) {
            return;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        logger.info("Success login: " + userDetails.getUsername());

        User user = userService.findByUserName(authentication.getName());
        if(user.getTries() != null && user.getTries() > 0) {
            user.setTries(0);
            userService.update(user, user.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        logger.error("Login failed: " + exception.getMessage());
        try {
            User user = userService.findByUserName(authentication.getName());
            int tries = user.getTries() == null ? 0 : user.getTries();
            logger.info("Actual try: " + user.getTries());
            user.setTries(tries + 1);

            if (user.getTries() >= 3) {
                logger.error("User has reached his maximum tries, is now disabled ");
                user.setEnabled(false);
            }

            userService.update(user, user.getId());
        } catch (FeignException e) {
            logger.error(String.format("The user %s not exists", authentication.getName()));
        }
    }
}
