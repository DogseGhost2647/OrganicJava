package com.example.organic.Service.DAO;

import java.util.List;

public interface IDAO <T, ID>{

    List<T> getAll();
    T create(T entity);
    T upgrade(T entity);
    T getById(ID id);
    void delete(ID id);
    
}
