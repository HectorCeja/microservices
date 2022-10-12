package com.ceja.users.domain.repositories;

import ceja.commons.models.entities.users.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @RestResource(path = "name")
    User findByUserName(String userName);

    @Query("select u from User u where u.userName=?1")
    User getPorName(String userName);

}
