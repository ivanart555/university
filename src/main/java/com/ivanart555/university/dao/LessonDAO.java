package com.ivanart555.university.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.ivanart555.university.dao.generic.GenericDAO;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;

public interface LessonDAO extends GenericDAO<Lesson, Integer> {

    List<Lesson> getByDateTimeIntervalAndGroupId(Integer groupId, LocalDateTime lessonStart, LocalDateTime lessonEnd);
    
    List<Lesson> getByDateTimeIntervalAndStudentId(Integer studentId, LocalDateTime lessonStart, LocalDateTime lessonEnd);
    
    List<Lesson> getByDateTimeIntervalAndLecturerId(Integer lecturerId, LocalDateTime lessonStart, LocalDateTime lessonEnd);
    
    void assignLessonToGroup(Integer lessonId, Integer groupId) throws DAOException;

}
