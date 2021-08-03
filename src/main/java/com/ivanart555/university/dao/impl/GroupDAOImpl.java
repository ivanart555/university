package com.ivanart555.university.dao.impl;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.mappers.GroupMapper;

@Component
public class GroupDAOImpl implements GroupDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDAOImpl.class);
    private final Environment env;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    @Override
    public List<Group> getAll() {
        LOGGER.debug("Trying to get all Groups.");
        return jdbcTemplate.query(env.getProperty("sql.groups.get.all"), new GroupMapper());
    }

    @Override
    public Group getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Group by id: {}", id);
        Group group = new Group();
        try {
            group = jdbcTemplate.queryForObject(env.getProperty("sql.groups.get.byId"), new Object[] { id },
                    new GroupMapper());
        } catch (EmptyResultDataAccessException e) {
            String msg = format("Group with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        } catch (DataAccessException e) {
            String msg = format("Unable to get Group with ID '%s'", id);
            throw new QueryNotExecuteException(msg);
        }
        LOGGER.debug("Found:'{}'", group);

        return group;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Group by id: {}", id);
        jdbcTemplate.update(env.getProperty("sql.groups.delete"), id);
    }

    @Override
    public void update(Group group) throws DAOException {
        LOGGER.debug("Trying to update {}", group);
        try {
            jdbcTemplate.update(env.getProperty("sql.groups.update"), group.getName(), group.getId());
        } catch (DataAccessException e) {
            String msg = format("Unable to update '%s'", group);
            throw new QueryNotExecuteException(msg);
        }
    }

    @Override
    public void create(Group group) throws DAOException {
        LOGGER.debug("Trying to create {}", group);
        try {
            jdbcTemplate.update(env.getProperty("sql.groups.create"), group.getName());
        } catch (DuplicateKeyException e) {
            throw new DAOException("Group " + group.getName() + " already exists.", e);
        } catch (DataAccessException e) {
            String msg = format("Unable to create '%s'", group);
            throw new QueryNotExecuteException(msg);
        }
    }
}
