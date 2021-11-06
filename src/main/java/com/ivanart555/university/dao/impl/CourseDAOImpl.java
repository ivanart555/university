package com.ivanart555.university.dao.impl;

import static java.lang.String.format;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.mappers.CourseMapper;

@Component
@Transactional
public class CourseDAOImpl implements CourseDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Course> getAll() {
        LOGGER.debug("Trying to get all Courses.");
        return entityManager.createQuery("FROM Course").getResultList();
    }

    @Override
    public Course getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Course by id: {}", id);
        Course course = entityManager.find(Course.class, id);
        if (course == null) {
            String msg = format("Course with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }

        LOGGER.debug("Found:'{}'", course);

        return course;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Course by id: {}", id);
        Course course = entityManager.find(Course.class, id);

        if (course != null) {
            entityManager.remove(course);
        }
    }

    @Override
    public void update(Course course) throws DAOException {
        LOGGER.debug("Trying to update {}", course);
        entityManager.merge(course);
    }

    @Override
    public void create(Course course) throws DAOException {
        LOGGER.debug("Trying to create {}", course);
        entityManager.persist(course);
    }
}