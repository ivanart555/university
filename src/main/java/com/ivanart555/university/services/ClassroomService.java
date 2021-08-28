package com.ivanart555.university.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface ClassroomService extends GenericService<Classroom, Integer> {

    Page<Classroom> findPaginated(Pageable pageable) throws ServiceException;

}
