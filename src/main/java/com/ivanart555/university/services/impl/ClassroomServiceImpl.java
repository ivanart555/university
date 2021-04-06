package com.ivanart555.university.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.ClassroomService;

public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    ClassroomDAO classroomDAO;

    @Override
    public List<Classroom> getAll() throws ServiceException {
        List<Classroom> classrooms = new ArrayList<>();
        try {
            classrooms = classroomDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
        return classrooms;
    }

    @Override
    public Classroom getById(Integer id) throws ServiceException {
        Classroom classroom = classroomDAO.getById(id);
        if (classroom == null) {
            throw new ServiceException("There is no Classroom with such id:" + id);
        }

        return classroom;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            classroomDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public void update(Classroom classroom) throws ServiceException {
        try {
            classroomDAO.update(classroom);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public void create(Classroom classroom) throws ServiceException {
        try {
            classroomDAO.create(classroom);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
    }

}
