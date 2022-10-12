package com.ceja.oauth.clients;

import ceja.commons.models.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("users-service")
public interface UserFeignClient {

    @GetMapping("/users/search/name")
    User findByUserName(@RequestParam("userName") String userName);

    @GetMapping("/users/{id}")
    User findById(@PathVariable("id") Long id);

}
