package com.ivanart555.university.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface StudentService extends GenericService<Student, Integer> {

    List<Student> getAllActive() throws ServiceException;

    void addStudentToGroup(Student student, Group group) throws ServiceException;

    List<Lesson> getDaySchedule(Student student, LocalDate day) throws ServiceException;

    Page<Student> findPaginated(Pageable pageable) throws ServiceException;
    
}
