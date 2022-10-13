package com.ceja.oauth.services;

import brave.Tracer;
import ceja.commons.models.entities.users.User;
import com.ceja.oauth.clients.UserFeignClient;
import feign.FeignException;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService, IUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Tracer tracer;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userFeignClient.findByUserName(username);
            if (user == null) {
                String errorMessage = "Login error: the user " + username + " not exists.";
                logger.error(errorMessage);
                throw new UsernameNotFoundException(errorMessage);
            }
            List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                    .peek(authority -> logger.info("Role: " + authority.getAuthority()))
                    .collect(Collectors.toList());

            logger.info("User authenticated: " + username);

            return new org.springframework.security.core.userdetails.User(user.getUserName(),
                    user.getPassword(),
                    user.getEnabled(),
                    true,
                    true,
                    true,
                    authorities);
        }catch (FeignException e) {
            String message = "Login error: the following user doesn't exist: " + username;
            logger.error(message);
            tracer.currentSpan().tag("message.error", message + ": " + e.getMessage());
            throw new UsernameNotFoundException(message);
        }
    }

    @Override
    public User findByUserName(String userName) {
        return userFeignClient.findByUserName(userName);
    }

    @Override
    public User update(User user, Long id) {
        return userFeignClient.update(user, id);
    }

}
