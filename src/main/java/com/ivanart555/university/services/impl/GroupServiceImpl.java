package com.ivanart555.university.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.services.GroupService;

public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupDAO groupDAO;

    @Override
    public List<Group> getAll() throws ServiceException {
        List<Group> groups = new ArrayList<>();
        try {
            groups = groupDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
        return groups;
    }

    @Override
    public Group getById(Integer id) throws ServiceException {
        Group group = groupDAO.getById(id);
        if (group == null) {
            throw new ServiceException("There is no Group with such id:" + id);
        }
        
        return group;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            groupDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public void update(Group group) throws ServiceException {
        try {
            groupDAO.update(group);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public void create(Group group) throws ServiceException {
        try {
            groupDAO.create(group);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
    }
}
