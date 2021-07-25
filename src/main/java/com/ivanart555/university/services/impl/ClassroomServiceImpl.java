package com.ivanart555.university.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.DAOException;

import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.ClassroomService;

@Component
public class ClassroomServiceImpl implements ClassroomService {
    private ClassroomDAO classroomDAO;

    @Autowired
    public ClassroomServiceImpl(ClassroomDAO classroomDAO) {
        this.classroomDAO = classroomDAO;
    }

    @Override
    public List<Classroom> getAll() throws ServiceException {
        List<Classroom> classrooms = classroomDAO.getAll();
        if (classrooms.isEmpty()) {
            throw new ServiceException("There is no Classrooms in database");
        }
        return classrooms;
    }

    @Override
    public Classroom getById(Integer id) throws ServiceException {
        Classroom classroom;
        try {
            classroom = classroomDAO.getById(id);
        } catch (DAOException e) {
            throw new ServiceException("Unable to get Classroom from database.", e);
        }

        return classroom;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        classroomDAO.delete(id);
    }

    @Override
    public void update(Classroom classroom) throws ServiceException {
        try {
            classroomDAO.update(classroom);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Classroom.", e);
        }
    }

    @Override
    public void create(Classroom classroom) throws ServiceException {
        try {
            classroomDAO.create(classroom);
        } catch (DAOException e) {
            throw new ServiceException("Unable to create Classroom.", e);
        }
    }
}
