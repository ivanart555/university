package com.ivanart555.university.services.impl;

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.repository.LessonRepository;
import com.ivanart555.university.services.GroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;
    private LessonRepository lessonRepository;

    @Override
    public List<Group> findAll() throws ServiceException {
        List<Group> groups = groupRepository.findAll();
        log.info("All Groups received successfully.");
        return groups;
    }

    @Override
    public Page<Group> findAll(Pageable pageable) throws ServiceException {
        Page<Group> groups = groupRepository.findAll(pageable);
        log.info("All Groups received successfully.");
        return groups;
    }

    @Override
    public Group findById(Integer id) throws ServiceException {
        Group group = null;
        try {
            group = groupRepository.findById(id).orElseThrow(() -> new ServiceException(
                    String.format("Group with id %d not found!", id)));
        } catch (ServiceException e) {
            log.warn("Group with id {} not found!", id);
        }
        log.info("Group with id {} received successfully.", id);

        return group;
    }

    @Override
    public void deleteById(Integer id) throws ServiceException {
        groupRepository.deleteById(id);
        log.info("Group with id {} deleted successfully.", id);
    }

    @Override
    public int save(Group group) throws ServiceException {
        Group createdGroup = groupRepository.save(group);
        log.info("Group with id {} saved successfully.", createdGroup.getId());
        return createdGroup.getId();
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
            log.info("Schedule for Group with id {} not found", group.getId());
        }
        log.info("Schedule for Group with id {} received successfully.", group.getId());

        return lessons;
    }
}
