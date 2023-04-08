package com.example.accesa_application.service;

import com.example.accesa_application.domain.User;

public interface IUserService<ID> extends IService<ID, User<ID>> {
    User<ID> getUserByEmail(String email);

    User<ID> getUserByUsername(String username);
}
