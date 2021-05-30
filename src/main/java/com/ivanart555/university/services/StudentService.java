package com.ivanart555.university.services;

import java.time.LocalDate;
import java.util.List;

import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface StudentService extends GenericService<Student, Integer> {

    List<Student> getAllActive() throws ServiceException;

    void assignStudentToGroup(Student student, Integer groupId) throws ServiceException;

    void assignStudentToCourse(Student student, Integer courseId) throws ServiceException;

    List<Lesson> getDaySchedule(Student student, LocalDate day) throws ServiceException;

}
