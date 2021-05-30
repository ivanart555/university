package com.ivanart555.university.services;

import java.util.List;

import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface LecturerService extends GenericService<Lecturer, Integer> {
    
    List<Lecturer> getAllActive() throws ServiceException;
    
    void addLecturerToCourse(Lecturer lecturer, Integer courseId) throws ServiceException;
    
    void addLecturerToGroup(Lecturer lecturer, Integer groupId) throws ServiceException;
    
}
