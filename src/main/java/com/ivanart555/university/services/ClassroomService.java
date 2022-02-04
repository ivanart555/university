package com.ivanart555.university.services;

import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassroomService extends GenericService<Classroom, Integer> {

    Page<Classroom> findAll(Pageable pageable) throws ServiceException;

}
