package com.ivanart555.university.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.mappers.ClassroomMapper;

import static java.lang.String.format;

@Component
public class ClassroomDAOImpl implements ClassroomDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomDAOImpl.class);
    private final Environment env;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassroomDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    @Override
    public List<Classroom> getAll() {
        LOGGER.debug("Trying to get all Classrooms.");
        return jdbcTemplate.query(env.getProperty("sql.classrooms.get.all"), new ClassroomMapper());
    }

    @Override
    public Classroom getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Classroom by id: {}", id);
        Classroom classroom = new Classroom();
        try {
            classroom = jdbcTemplate.queryForObject(env.getProperty("sql.classrooms.get.byId"), new Object[] { id },
                    new ClassroomMapper());
        } catch (EmptyResultDataAccessException e) {
            String msg = format("Classroom with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        } catch (DataAccessException e) {
            String msg = format("Unable to get Classroom with ID '%s'", id);
            throw new QueryNotExecuteException(msg);
        }
        LOGGER.debug("Found:'{}'", classroom);

        return classroom;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Classroom by id: {}", id);
        jdbcTemplate.update(env.getProperty("sql.classrooms.delete"), id);
    }

    @Override
    public void update(Classroom classRoom) throws DAOException {
        LOGGER.debug("Trying to update {}", classRoom);
        try {
            jdbcTemplate.update(env.getProperty("sql.classrooms.update"), classRoom.getName(), classRoom.getId());
        } catch (DataAccessException e) {
            String msg = format("Unable to update '%s'", classRoom);
            throw new QueryNotExecuteException(msg);
        }
    }

    @Override
    public void create(Classroom classRoom) throws DAOException {
        LOGGER.debug("Trying to create {}", classRoom);
        try {
            jdbcTemplate.update(env.getProperty("sql.classrooms.create"), classRoom.getName());
        } catch (DataAccessException e) {
            String msg = format("Unable to create '%s'", classRoom);
            throw new QueryNotExecuteException(msg);
        }
    }
}
