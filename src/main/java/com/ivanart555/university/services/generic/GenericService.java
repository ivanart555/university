package com.ivanart555.university.services.generic;

import java.io.Serializable;
import java.util.List;

import com.ivanart555.university.exception.ServiceException;

public interface GenericService<T, K extends Serializable> {

    List<T> getAll() throws ServiceException;

    T getById(K id) throws ServiceException;

    void delete(K id) throws ServiceException;

    void update(T t) throws ServiceException;

    void create(T t) throws ServiceException;

}
