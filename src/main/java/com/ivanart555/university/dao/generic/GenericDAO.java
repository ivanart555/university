package com.ivanart555.university.dao.generic;

import java.io.Serializable;
import java.util.List;

import com.ivanart555.university.exception.DAOException;

public interface GenericDAO<T, K extends Serializable> {

    List<T> getAll();

    T getById(K id) throws DAOException;

    void delete(K id);

    void update(T t) throws DAOException;

    void create(T t) throws DAOException;

}
