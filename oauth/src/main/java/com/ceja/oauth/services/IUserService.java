package com.ceja.oauth.services;

import ceja.commons.models.entities.users.User;

public interface IUserService {

    User findByUserName(String userName);

    User update(User user, Long id);

}
