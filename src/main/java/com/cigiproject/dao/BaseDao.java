package main.java.com.cigiproject.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    Optional<T> findById(Integer id);
    List<T> findAll();
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(Integer id);
    
} 
