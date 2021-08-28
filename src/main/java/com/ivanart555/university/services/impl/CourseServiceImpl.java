package com.ivanart555.university.services.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.CourseService;

@Component
public class CourseServiceImpl implements CourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);
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
        LOGGER.info("All Courses received successfully.");

        return courses;
    }

    @Override
    public Course getById(Integer id) throws ServiceException {
        Course course = null;
        try {
            course = courseDAO.getById(id);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Course with id {} not found!", id);
        } catch (QueryNotExecuteException e) {
            LOGGER.error("Query didn't execute. Check SQL query.");
        } catch (DAOException e) {
            LOGGER.error("Something got wrong with DAO.");
            throw new ServiceException("Unable to get Course by id.", e);
        }
        LOGGER.info("Course with id {} received successfully.", id);

        return course;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        courseDAO.delete(id);
        LOGGER.info("Course with id {} deleted successfully.", id);
    }

    @Override
    public void update(Course course) throws ServiceException {
        try {
            courseDAO.update(course);
        } catch (QueryNotExecuteException e) {
            LOGGER.error("Query didn't execute. Check SQL query.");
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Course.", e);
        }
        LOGGER.info("Course with id {} updated successfully.", course.getId());
    }

    @Override
    public void create(Course course) throws ServiceException {
        try {
            courseDAO.create(course);
        } catch (DAOException e) {
            throw new ServiceException("Unable to create Course.", e);
        }
        LOGGER.info("Course with id {} created successfully.", course.getId());
    }

    @Override
    public Page<Course> findPaginated(Pageable pageable) throws ServiceException {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Course> allCourses = getAll();
        int coursesSize = allCourses.size();

        List<Course> list;

        if (coursesSize < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, coursesSize);
            list = allCourses.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), coursesSize);
    }
}
