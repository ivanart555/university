package com.ivanart555.university.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivanart555.university.dto.LessonDto;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface LessonService extends GenericService<Lesson, Integer> {

    void assignLessonToDateTimeForGroup(Lesson lesson, LocalDateTime lessonStart, LocalDateTime lessonEnd,
            Integer groupId)
            throws ServiceException;

    Page<LessonDto> findAll(Pageable pageable) throws ServiceException;

    void save(LessonDto lessonDto) throws ServiceException;
}
