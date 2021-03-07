package com.ivanart555.university.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.mappers.ClassroomMapper;

@Component
public class ClassroomDAOImpl implements ClassroomDAO {

    @Autowired
    private Environment env;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassroomDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Classroom> getAll() {
        return jdbcTemplate.query(env.getProperty("sql.classrooms.get.all"), new ClassroomMapper());
    }

    @Override
    public Classroom getById(Integer id) {
        return jdbcTemplate
                .query(env.getProperty("sql.classrooms.get.byId"), new Object[] { id },
                        new ClassroomMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(Integer id) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.classrooms.delete"), id);
    }

    @Override
    public void update(Classroom classRoom) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.classrooms.update"), classRoom.getRoomName(), classRoom.getRoomId());
    }

    @Override
    public void create(Classroom classRoom) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.classrooms.create"), classRoom.getRoomName());
    }
}
