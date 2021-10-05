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

import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.mappers.LecturerMapper;

@Component
public class LecturerDAOImpl implements LecturerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerDAOImpl.class);
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
        LOGGER.debug("Trying to get all Lecturers.");
        return jdbcTemplate.query(env.getProperty("sql.lecturers.get.all"), new LecturerMapper());
    }

    @Override
    public List<Lecturer> getAllActive() {
        LOGGER.debug("Trying to get all active Lecturers.");
        return jdbcTemplate.query(env.getProperty("sql.lecturers.get.all.active"), new LecturerMapper());
    }

    @Override
    public Lecturer getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Lecturer by id: {}", id);
        Lecturer lecturer = new Lecturer();
        try {
            lecturer = jdbcTemplate.queryForObject(env.getProperty("sql.lecturers.get.byId"), new Object[] { id },
                    new LecturerMapper());
        } catch (EmptyResultDataAccessException e) {
            String msg = format("Lecturer with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        } catch (DataAccessException e) {
            String msg = format("Unable to get Lecturer with ID '%s'", id);
            throw new QueryNotExecuteException(msg);
        }
        LOGGER.debug("Found:'{}'", lecturer);

        return lecturer;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Lecturer by id: {}", id);
        jdbcTemplate.update(env.getProperty("sql.lecturers.delete"), id);
    }

    @Override
    public void update(Lecturer lecturer) throws DAOException {
        LOGGER.debug("Trying to update {}", lecturer);
        try {
            jdbcTemplate.update(env.getProperty("sql.lecturers.update"), lecturer.getFirstName(),
                    lecturer.getLastName(),
                    lecturer.isActive(),
                    lecturer.getId());
        } catch (DataAccessException e) {
            String msg = format("Unable to update '%s'", lecturer);
            throw new QueryNotExecuteException(msg);
        }
    }

    @Override
    public void create(Lecturer lecturer) throws DAOException {
        LOGGER.debug("Trying to create {}", lecturer);
        try {
            jdbcTemplate.update(env.getProperty("sql.lecturers.create"), lecturer.getFirstName(),
                    lecturer.getLastName(),
                    IS_ACTIVE);
        } catch (DataAccessException e) {
            String msg = format("Unable to create '%s'", lecturer);
            throw new QueryNotExecuteException(msg);
        }
    }

    @Override
    public void addLecturerToCourse(Integer lecturerId, Integer courseId) throws DAOException {
        LOGGER.debug("Trying to add Lecturer with id:{} to Course with id:{}", lecturerId, courseId);
        jdbcTemplate.update(env.getProperty("sql.lecturers.add.lecturerToCourse"), lecturerId, courseId);
    }

    @Override
    public void addLecturerToGroup(Integer lecturerId, Integer groupId) throws DAOException {
        LOGGER.debug("Trying to add Lecturer with id:{} to Group with id:{}", lecturerId, groupId);
        jdbcTemplate.update(env.getProperty("sql.lecturers.add.lecturerToGroup"), lecturerId, groupId);
    }
}
