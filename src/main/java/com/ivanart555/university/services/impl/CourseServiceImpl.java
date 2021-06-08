package com.ivanart555.university.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.CourseService;

@Component
public class CourseServiceImpl implements CourseService {
    private CourseDAO courseDAO;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public List<Course> getAll() throws ServiceException {
        List<Course> courses = courseDAO.getAll();
        if (courses.isEmpty()) {
            throw new ServiceException("There are no Courses in database");
        }
        return courses;
    }

    @Override
    public Course getById(Integer id) throws ServiceException {
        Course course = courseDAO.getById(id);
        if (course == null) {
            throw new ServiceException("There is no Course with such id:" + id);
        }

        return course;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
            courseDAO.delete(id);
    }

    @Override
    public void update(Course course) throws ServiceException {
        try {
            courseDAO.update(course);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Course.", e);
        }
    }

    @Override
    public void create(Course course) throws ServiceException {
        try {
            courseDAO.create(course);
        } catch (DAOException e) {
            throw new ServiceException("Unable to create Course.", e);
        }
    }
}
