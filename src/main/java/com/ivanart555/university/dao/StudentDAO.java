package com.ivanart555.university.dao;

import com.ivanart555.university.exception.DAOException;

import java.util.List;

import com.ivanart555.university.dao.generic.GenericDAO;
import com.ivanart555.university.entities.Student;

public interface StudentDAO extends GenericDAO<Student, Integer> {
    
    List<Student> getAllActive();
    
    List<Student> getStudentsByGroupId(Integer groupId) throws DAOException;
    
    void addStudentToCourse(Integer studentId, Integer courseId) throws DAOException;

}
