package com.ivanart555.university.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.DAOException;

@Component
public class GroupDAOImpl implements GroupDAO {
    @Value("${getAllGroups}")
    private String getAllGroups;

    @Value("${getGroupById}")
    private String getGroupById;

    @Value("${deleteGroup}")
    private String deleteGroup;

    @Value("${updateGroup}")
    private String updateGroup;

    @Value("${createGroup}")
    private String createGroup;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(getAllGroups, new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public Group getById(Integer id) {
        return jdbcTemplate.query(getGroupById, new Object[] { id }, new BeanPropertyRowMapper<>(Group.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(Integer id) throws DAOException {
        jdbcTemplate.update(deleteGroup, id);
    }

    @Override
    public void update(Group group) throws DAOException {
        jdbcTemplate.update(updateGroup, group.getGroupName(), group.getGroupId());
    }

    @Override
    public void create(Group group) throws DAOException {
        jdbcTemplate.update(createGroup, group.getGroupName());
    }
}
