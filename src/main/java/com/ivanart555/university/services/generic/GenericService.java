package com.ivanart555.university.services.generic;

import java.io.Serializable;
import java.util.List;

import com.ivanart555.university.exception.ServiceException;

public interface GenericService<T, K extends Serializable> {

    List<T> findAll() throws ServiceException;

    T findById(K id) throws ServiceException;
    
    void save(T t);
    
    void delete(K id) throws ServiceException;
}
