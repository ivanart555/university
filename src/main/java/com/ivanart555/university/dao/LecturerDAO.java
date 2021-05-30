package com.ivanart555.university.dao;

import java.util.List;

import com.ivanart555.university.dao.generic.GenericDAO;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.DAOException;

public interface LecturerDAO extends GenericDAO<Lecturer, Integer> {
    
    List<Lecturer> getAllActive();
    
    void addLecturerToCourse(Integer lecturerId, Integer courseId) throws DAOException;
    
    void addLecturerToGroup(Integer lecturerId, Integer groupId) throws DAOException;

}
