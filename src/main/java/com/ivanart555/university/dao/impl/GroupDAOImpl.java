package com.ivanart555.university.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.mappers.GroupMapper;

@Component
public class GroupDAOImpl implements GroupDAO {
    private final Environment env;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(env.getProperty("sql.groups.get.all"), new GroupMapper());
    }

    @Override
    public Group getById(Integer id) {
        return jdbcTemplate
                .query(env.getProperty("sql.groups.get.byId"), new Object[] { id },
                        new GroupMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(env.getProperty("sql.groups.delete"), id);
    }

    @Override
    public void update(Group group) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.groups.update"), group.getName(), group.getId());
    }

    @Override
    public void create(Group group) throws DAOException {
        try {
            jdbcTemplate.update(env.getProperty("sql.groups.create"), group.getName());
        } catch (DuplicateKeyException e) {
            throw new DAOException("Group " + group.getName() + " already exists.", e);
        }
    }
}