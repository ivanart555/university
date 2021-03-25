package com.ivanart555.university.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit.jupiter.*;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.DAOException;

@SpringJUnitConfig(TestSpringConfig.class)
class GroupDAOImplTest {

    @Autowired
    private Environment env;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GroupDAO groupDAO;

    private static ResourceDatabasePopulator sqlScript;

    private static final String CREATE_TABLES_SQL_SCRIPT = "scripts/create/tables.sql";
    private static final String WIPE_TABLES_SQL_SCRIPT = "scripts/wipe/tables.sql";
    private static final String GROUP_ID = "group_id";
    private static final String GROUP_NAME = "group_name";
    private static final String CONNECTION_ERROR = "Could not connect to database or SQL query error.";

    @BeforeEach
    public void createTables() {
        sqlScript = new ResourceDatabasePopulator();
        sqlScript.addScript(new ClassPathResource(CREATE_TABLES_SQL_SCRIPT));
        DatabasePopulatorUtils.execute(sqlScript, jdbcTemplate.getDataSource());
    }

    @AfterEach
    void wipeTables() throws Exception {
        sqlScript = new ResourceDatabasePopulator();
        sqlScript.addScript(new ClassPathResource(WIPE_TABLES_SQL_SCRIPT));
        DatabasePopulatorUtils.execute(sqlScript, jdbcTemplate.getDataSource());
    }

    @Test
    void shouldAddGroupToDatabase_whenCalledCreate() throws DAOException {
        Group expectedGroup = new Group(1, "AB-01");
        groupDAO.create(expectedGroup);

        String sql = env.getProperty("sql.groups.get.byId");
        Group actualGroup = null;
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, expectedGroup.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt(GROUP_ID);
                    String groupName = rs.getString(GROUP_NAME);
                    actualGroup = new Group(id, groupName);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldReturnGroupFromDatabase_whenGivenId() throws DAOException {
        Group expectedGroup = new Group(1, "AB-01");
        groupDAO.create(expectedGroup);

        Group actualGroup = groupDAO.getById(expectedGroup.getId());
        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldReturnAllGroupsFromDatabase_whenCalledGetAll() throws DAOException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group(1, "AB-01"));
        expectedGroups.add(new Group(2, "BA-12"));
        expectedGroups.add(new Group(3, "AC-06"));

        for (Group Group : expectedGroups) {
            groupDAO.create(Group);
        }
        assertEquals(expectedGroups, groupDAO.getAll());
    }

    @Test
    void shouldUpdateGroupsDataAtDatabase_whenCalledUpdate() throws DAOException {
        Group expectedGroup = new Group(1, "AB-01");
        groupDAO.create(expectedGroup);

        expectedGroup.setName("BA-12");

        groupDAO.update(expectedGroup);

        Group actualGroup = groupDAO.getById(expectedGroup.getId());
        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void shouldDeleteGroupFromDatabase_whenGivenId() throws DAOException {
        Group group = new Group(1, "AB-01");
        groupDAO.create(group);

        groupDAO.delete(group.getId());
        assertNull(groupDAO.getById(group.getId()));
    }
}
