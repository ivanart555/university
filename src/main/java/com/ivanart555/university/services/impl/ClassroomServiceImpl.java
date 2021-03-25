package com.ivanart555.university.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.BusinessException;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.services.ClassroomService;

public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    ClassroomDAO classroomDAO;
    
    @Override
    public List<Classroom> getAll() throws BusinessException, DAOException {
        return classroomDAO.getAll();
    }

    @Override
    public Classroom getById(Integer id) throws BusinessException, DAOException {
        return classroomDAO.getById(id);
    }

    @Override
    public void delete(Integer id) throws BusinessException, DAOException {
        classroomDAO.delete(id);
    }

    @Override
    public void update(Classroom t) throws BusinessException, DAOException {
        classroomDAO.update(t);
    }

    @Override
    public void create(Classroom t) throws BusinessException, DAOException {
        classroomDAO.create(t);
    }

}
