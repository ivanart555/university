package com.ivanart555.university.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface LecturerService extends GenericService<Lecturer, Integer> {

    List<Lecturer> getAllActive() throws ServiceException;

    void addLecturerToCourse(Lecturer lecturer, Integer courseId) throws ServiceException;

    List<Lesson> getDaySchedule(Lecturer lecturer, LocalDate day) throws ServiceException;

    Page<Lecturer> findPaginated(Pageable pageable) throws ServiceException;
}
