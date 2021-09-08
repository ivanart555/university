package com.ivanart555.university.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface GroupService extends GenericService<Group, Integer> {

    Page<Group> findPaginated(Pageable pageable) throws ServiceException;

}
