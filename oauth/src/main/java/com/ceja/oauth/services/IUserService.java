package com.ceja.oauth.services;

import ceja.commons.models.entities.User;

public interface IUserService {

    User findByUserName(String userName);

}