package com.ivanart555.university.services.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.services.GroupService;

@Component
public class GroupServiceImpl implements GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
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
        LOGGER.info("All Groups received successfully.");

        return groups;
    }

    @Override
    public Group getById(Integer id) throws ServiceException {
        Group group = null;
        try {
            group = groupDAO.getById(id);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Group with id {} not found!", id);
        } catch (QueryNotExecuteException e) {
            LOGGER.error("Query didn't execute. Check SQL query.");
        } catch (DAOException e) {
            LOGGER.error("Something got wrong with DAO.");
            throw new ServiceException("Unable to get Group by id.", e);
        }
        LOGGER.info("Group with id {} received successfully.", id);

        return group;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        groupDAO.delete(id);
        LOGGER.info("Group with id {} deleted successfully.", id);
    }

    @Override
    public void update(Group group) throws ServiceException {
        try {
            groupDAO.update(group);
        } catch (QueryNotExecuteException e) {
            LOGGER.error("Query didn't execute. Check SQL query.");
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Group.", e);
        }
        LOGGER.info("Group with id {} updated successfully.", group.getId());
    }

    @Override
    public void create(Group group) throws ServiceException {
        try {
            groupDAO.create(group);
        } catch (DAOException e) {
            throw new ServiceException("Unable to create Group", e);
        }
        LOGGER.info("Group with id {} created successfully.", group.getId());
    }

    @Override
    public Page<Group> findPaginated(Pageable pageable) throws ServiceException {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Group> allGroups = getAll();
        int groupsSize = allGroups.size();

        List<Group> list;

        if (groupsSize < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, groupsSize);
            list = allGroups.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), groupsSize);
    }
}
