package com.ivanart555.university.services;

import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService extends GenericService<Course, Integer> {

    Page<Course> findAll(Pageable pageable) throws ServiceException;

}
