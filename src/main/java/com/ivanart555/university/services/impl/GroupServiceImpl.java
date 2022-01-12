package com.ivanart555.university.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.repository.LessonRepository;
import com.ivanart555.university.services.GroupService;

@Component
public class GroupServiceImpl implements GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
    private GroupRepository groupRepository;
    private LessonRepository lessonRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> findAll() throws ServiceException {
        List<Group> groups = groupRepository.findAll();
        LOGGER.info("All Groups received successfully.");
        return groups;
    }

    @Override
    public Page<Group> findAll(Pageable pageable) throws ServiceException {
        Page<Group> groups = groupRepository.findAll(pageable);
        LOGGER.info("All Groups received successfully.");
        return groups;
    }

    @Override
    public Group findById(Integer id) throws ServiceException {
        Group group = null;
        try {
            group = groupRepository.findById(id).orElseThrow(() -> new ServiceException(
                    String.format("Group with id %d not found!", id)));
        } catch (ServiceException e) {
            LOGGER.warn("Group with id {} not found!", id);
        }
        LOGGER.info("Group with id {} received successfully.", id);

        return group;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        groupRepository.deleteById(id);
        LOGGER.info("Group with id {} deleted successfully.", id);
    }

    @Override
    public void save(Group group) throws ServiceException {
        groupRepository.save(group);
        LOGGER.info("Group with id {} saved successfully.", group.getId());
    }

    @Override
    public List<Lesson> getDaySchedule(Group group, LocalDate day) throws ServiceException {
        LocalDateTime startDateTime = day.atStartOfDay();
        LocalDateTime endDateTime = day.atTime(23, 59, 59);
        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = lessonRepository.findAllByGroupIdAndLessonStartLessThanEqualAndLessonEndGreaterThanEqual(
                    group.getId(), endDateTime, startDateTime);
        } catch (EntityNotFoundException e) {
            LOGGER.info("Schedule for Group with id {} not found", group.getId());
        }
        LOGGER.info("Schedule for Group with id {} received successfully.", group.getId());

        return lessons;
    }
}
