package com.example.accesa_application.service;

import com.example.accesa_application.domain.User;
import com.example.accesa_application.repository.IUserRepository;

import java.util.Properties;

public class UserService implements IUserService<Integer>{

    Properties props;
    private final IUserRepository<Integer> userRepository;

    public UserService(Properties props, IUserRepository<Integer> userRepository) {
        this.props = props;
        this.userRepository = userRepository;
    }

    @Override
    public void add(User<Integer> elem) {
        userRepository.add(elem);
    }

    @Override
    public void update(Integer id, User<Integer> elem) {
        userRepository.update(id,elem);
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public User<Integer> findAfterId(Integer id) {
        return userRepository.findAfterId(id);
    }

    @Override
    public Iterable<User<Integer>> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User<Integer> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User<Integer> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
