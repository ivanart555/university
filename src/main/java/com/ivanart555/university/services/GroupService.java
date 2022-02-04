package com.ivanart555.university.services;

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface GroupService extends GenericService<Group, Integer> {

    Page<Group> findAll(Pageable pageable) throws ServiceException;

    List<Lesson> getDaySchedule(Group group, LocalDate day) throws ServiceException;

}
