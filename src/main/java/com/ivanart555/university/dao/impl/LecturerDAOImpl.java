package com.ivanart555.university.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.mappers.LecturerMapper;

@Component
public class LecturerDAOImpl implements LecturerDAO {
    private static final boolean IS_ACTIVE = true;
    private final Environment env;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LecturerDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    @Override
    public List<Lecturer> getAll() {
        return jdbcTemplate.query(env.getProperty("sql.lecturers.get.all"), new LecturerMapper());
    }
   
    @Override
    public List<Lecturer> getAllActive() {
        return jdbcTemplate.query(env.getProperty("sql.lecturers.get.all.active"), new LecturerMapper());
    }

    @Override
    public Lecturer getById(Integer id) {
        return jdbcTemplate
                .query(env.getProperty("sql.lecturers.get.byId"), new Object[] { id },
                        new LecturerMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(env.getProperty("sql.lecturers.delete"), id);
    }

    @Override
    public void update(Lecturer lecturer) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.lecturers.update"), lecturer.getFirstName(), lecturer.getLastName(),
                lecturer.getId());
    }

    @Override
    public void create(Lecturer lecturer) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.lecturers.create"), lecturer.getFirstName(), lecturer.getLastName(), IS_ACTIVE);
    }

    @Override
    public void addLecturerToCourse(Integer lecturerId, Integer courseId) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.lecturers.add.lecturerToCourse"), lecturerId, courseId);
    }

    @Override
    public void addLecturerToGroup(Integer lecturerId, Integer groupId) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.lecturers.add.lecturerToGroup"), lecturerId, groupId);
    }
}
