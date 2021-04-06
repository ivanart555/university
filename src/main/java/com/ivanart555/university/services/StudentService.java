package com.ivanart555.university.services;

import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.generic.GenericService;

public interface StudentService extends GenericService<Student, Integer> {
    
    public void assignStudentToGroup(Student student, Integer groupId) throws ServiceException;

}
