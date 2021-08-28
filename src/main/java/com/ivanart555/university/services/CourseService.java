package com.ivanart555.university.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface CourseService extends GenericService<Course, Integer> {

    Page<Course> findPaginated(Pageable pageable) throws ServiceException;

}
