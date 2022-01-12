package com.ivanart555.university.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface GroupService extends GenericService<Group, Integer> {

    Page<Group> findAll(Pageable pageable) throws ServiceException;

    List<Lesson> getDaySchedule(Group group, LocalDate day) throws ServiceException;

}
