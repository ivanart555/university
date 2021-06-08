package com.ivanart555.university.services;

import java.time.LocalDateTime;

import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface LessonService extends GenericService<Lesson, Integer> {

    void assignLessonToCourse(Lesson lesson, Integer courseId) throws ServiceException;

    void assignLessonToClassroom(Lesson lesson, Integer roomId) throws ServiceException;

    void assignLessonToLecturer(Lesson lesson, Integer lecturerId) throws ServiceException;
    
    void assignLessonToGroup(Lesson lesson, Integer groupId) throws ServiceException;

    void assignLessonToDateTimeForGroup(Lesson lesson, LocalDateTime lessonStart, LocalDateTime lessonEnd, Integer groupId)
            throws ServiceException;
}
