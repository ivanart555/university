package com.ivanart555.university.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.services.GroupService;

@Component
public class GroupServiceImpl implements GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
    private static final String SOMETHING_WRONG_WITH_DAO = "Something got wrong with DAO.";
    private GroupDAO groupDAO;
    private LessonDAO lessonDAO;

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
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
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
    public List<Lesson> getDaySchedule(Group group, LocalDate day) throws ServiceException {
        LocalDateTime startDateTime = day.atStartOfDay();
        LocalDateTime endDateTime = day.atTime(23, 59, 59);
        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = lessonDAO.getByDateTimeIntervalAndGroupId(group.getId(), startDateTime, endDateTime);
        } catch (EntityNotFoundException e) {
            LOGGER.info("Schedule for Group with id {} not found", group.getId());
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Schedule for Group.", e);
        }
        LOGGER.info("Schedule for Lecturer with id {} received successfully.", group.getId());

        return lessons;
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
