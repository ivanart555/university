package com.ivanart555.university.services;

import com.ivanart555.university.dto.LessonDto;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface LessonService extends GenericService<Lesson, Integer> {

    void assignLessonToDateTimeForGroup(Lesson lesson, LocalDateTime lessonStart, LocalDateTime lessonEnd,
                                        Integer groupId)
            throws ServiceException;

    Page<LessonDto> findAll(Pageable pageable) throws ServiceException;

   // Page<LessonDto> find(BooleanExpression booleanExpression, Pageable pageable) throws ServiceException;

    void save(LessonDto lessonDto) throws ServiceException;
}
