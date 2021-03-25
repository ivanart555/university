package com.ivanart555.university.services.generic;

import java.io.Serializable;
import java.util.List;

import com.ivanart555.university.exception.BusinessException;
import com.ivanart555.university.exception.DAOException;

public interface GenericService<T, K extends Serializable> {

    List<T> getAll() throws BusinessException, DAOException;

    T getById(K id) throws BusinessException, DAOException;

    void delete(K id) throws BusinessException, DAOException;

    void update(T t) throws BusinessException, DAOException;

    void create(T t) throws BusinessException, DAOException;

}
