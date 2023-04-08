package com.example.accesa_application.repository;

import com.example.accesa_application.domain.Entity;

public interface IRepository <ID, T extends Entity<ID>>{
    void add(T elem);
    void update(ID id, T elem);
    void delete(ID id);
    T findAfterId(ID id);
    Iterable<T> getAll();
}
