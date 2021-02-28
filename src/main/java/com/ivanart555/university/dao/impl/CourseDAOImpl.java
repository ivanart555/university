package com.ivanart555.university.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.DAOException;

@Component
public class CourseDAOImpl implements CourseDAO {
    @Value("${getAllCourses}")
    private String getAllCourses;

    @Value("${getCourseById}")
    private String getCourseById;

    @Value("${deleteCourse}")
    private String deleteCourse;

    @Value("${updateCourse}")
    private String updateCourse;

    @Value("${createCourse}")
    private String createCourse;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(getAllCourses, new BeanPropertyRowMapper<>(Course.class));
    }

    @Override
    public Course getById(Integer id) {
        return jdbcTemplate.query(getCourseById, new Object[] { id }, new BeanPropertyRowMapper<>(Course.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(Integer id) throws DAOException {
        jdbcTemplate.update(deleteCourse, id);
    }

    @Override
    public void update(Course course) throws DAOException {
        jdbcTemplate.update(updateCourse, course.getCourseName(), course.getCourseDescription(), course.getCourseId());
    }

    @Override
    public void create(Course course) throws DAOException {
        jdbcTemplate.update(createCourse, course.getCourseName(), course.getCourseDescription());
    }
}
