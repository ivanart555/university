package com.ivanart555.university.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.services.StudentService;

@Component
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentDAO studentDAO;

    @Autowired
    GroupDAO groupDAO;

    @Override
    public List<Student> getAll() throws ServiceException {
        List<Student> students = new ArrayList<>();
        try {
            students = studentDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
        return students;
    }

    @Override
    public Student getById(Integer id) throws ServiceException {
        Student student = studentDAO.getById(id);
        if (student == null) {
            throw new ServiceException("There is no Student with such id:" + id);
        }

        return student;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        try {
            studentDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public void update(Student student) throws ServiceException {
        try {
            studentDAO.update(student);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public void create(Student student) throws ServiceException {
        try {
            studentDAO.create(student);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public void assignStudentToGroup(Student student, Integer groupId) throws ServiceException {
        Group group;
        group = groupDAO.getById(groupId);

        if (group == null) {
            throw new ServiceException("Failed to assign student to group. There is no Group with such id:" + groupId);
        }

        student.setGroupId(groupId);
        try {
            studentDAO.update(student);
        } catch (DAOException e) {
            throw new ServiceException("", e);

        }
    }
}
