package com.ivanart555.university.dao.impl;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.mappers.CourseMapper;

@Component
public class CourseDAOImpl implements CourseDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseDAOImpl.class);
    private final Environment env;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    @Override
    public List<Course> getAll() {
        LOGGER.debug("Trying to get all Courses.");
        return jdbcTemplate.query(env.getProperty("sql.courses.get.all"), new CourseMapper());
    }

    @Override
    public Course getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Course by id: {}", id);
        Course course = new Course();
        try {
            course = jdbcTemplate.queryForObject(env.getProperty("sql.courses.get.byId"), new Object[] { id },
                    new CourseMapper());
        } catch (EmptyResultDataAccessException e) {
            String msg = format("Course with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        } catch (DataAccessException e) {
            String msg = format("Unable to get Course with ID '%s'", id);
            throw new QueryNotExecuteException(msg);
        }
        LOGGER.debug("Found:'{}'", course);

        return course;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Course by id: {}", id);
        jdbcTemplate.update(env.getProperty("sql.courses.delete"), id);
    }

    @Override
    public void update(Course course) throws DAOException {
        LOGGER.debug("Trying to update {}", course);
        try {
            jdbcTemplate.update(env.getProperty("sql.courses.update"), course.getName(),
                    course.getDescription(),
                    course.getId());
        } catch (DataAccessException e) {
            String msg = format("Unable to update '%s'", course);
            throw new QueryNotExecuteException(msg);
        }
    }

    @Override
    public void create(Course course) throws DAOException {
        LOGGER.debug("Trying to create {}", course);
        try {
            jdbcTemplate.update(env.getProperty("sql.courses.create"), course.getName(),
                    course.getDescription());
        } catch (DataAccessException e) {
            String msg = format("Unable to create '%s'", course);
            throw new QueryNotExecuteException(msg);
        }
    }
}