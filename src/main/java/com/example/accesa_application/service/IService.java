package com.example.accesa_application.service;

import com.example.accesa_application.domain.Entity;

import java.util.Properties;

public interface IService <ID, T extends Entity<ID>>{
    void add(T elem);
    void update(ID id, T elem);
    void delete(ID id);
    T findAfterId(ID id);
    Iterable<T> getAll();
}