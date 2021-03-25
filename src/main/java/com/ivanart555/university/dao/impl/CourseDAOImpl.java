package com.ivanart555.university.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.mappers.CourseMapper;

@Component
public class CourseDAOImpl implements CourseDAO {

    @Autowired
    private Environment env;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(env.getProperty("sql.courses.get.all"), new CourseMapper());
    }

    @Override
    public Course getById(Integer id) {
        return jdbcTemplate
                .query(env.getProperty("sql.courses.get.byId"), new Object[] { id },
                        new CourseMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(Integer id) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.courses.delete"), id);
    }

    @Override
    public void update(Course course) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.courses.update"), course.getName(),
                course.getDescription(),
                course.getId());
    }

    @Override
    public void create(Course course) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.courses.create"), course.getName(),
                course.getDescription());
    }
}
