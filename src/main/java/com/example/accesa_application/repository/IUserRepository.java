package com.example.accesa_application.repository;

import com.example.accesa_application.domain.User;

public interface IUserRepository<ID> extends IRepository<ID, User<ID>>{

    User<ID> getUserByEmail(String email);

    User<ID> getUserByUsername(String username);
}
