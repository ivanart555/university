package com.ivanart555.university.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.services.GroupService;

@Component
public class GroupServiceImpl implements GroupService {
    private GroupDAO groupDAO;

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public List<Group> getAll() throws ServiceException {
        List<Group> groups = groupDAO.getAll();
        if (groups.isEmpty()) {
            throw new ServiceException("There are no Groups in database");
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
        groupDAO.delete(id);
    }

    @Override
    public void update(Group group) throws ServiceException {
        try {
            groupDAO.update(group);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Group.", e);
        }
    }

    @Override
    public void create(Group group) throws ServiceException {
        try {
            groupDAO.create(group);
        } catch (DAOException e) {
            throw new ServiceException("Unable to create Group", e);
        }
    }
}
