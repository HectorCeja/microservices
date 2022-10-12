package com.ceja.oauth.clients;

import ceja.commons.models.entities.users.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("users-service")
public interface UserFeignClient {

    @GetMapping("/users/search/name")
    User findByUserName(@RequestParam("userName") String userName);

    @GetMapping("/users/{id}")
    User findById(@PathVariable("id") Long id);

    @PutMapping("/users/{id}")
    User update(@RequestBody User user, @PathVariable("id") Long id);

}
