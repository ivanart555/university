package com.ivanart555.university.dao.impl_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.DAOException;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class GroupDAOImplTest {
    private GroupDAO groupDAO;

    @Autowired
    private GroupDAOImplTest(GroupDAO groupDAO, Environment env, JdbcTemplate jdbcTemplate) {
        this.groupDAO = groupDAO;
    }

    @Test
    void shouldReturnAllGroupsFromDatabase_whenCalledGetAll() throws DAOException {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group("AB-01"));
        expectedGroups.add(new Group("BA-12"));
        expectedGroups.add(new Group("AC-06"));

        for (Group Group : expectedGroups) {
            groupDAO.create(Group);
        }
        assertEquals(expectedGroups, groupDAO.getAll());
    }
}
