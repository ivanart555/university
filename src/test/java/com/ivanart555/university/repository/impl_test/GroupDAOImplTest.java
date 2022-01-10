package com.ivanart555.university.repository.impl_test;

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
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.repository.GroupRepository;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class GroupRepositoryImplTest {
    private GroupRepository groupRepository;

    @Autowired
    private GroupRepositoryImplTest(GroupRepository groupRepository, Environment env, JdbcTemplate jdbcTemplate) {
        this.groupRepository = groupRepository;
    }

    @Test
    void shouldReturnAllGroupsFromDatabase_whenCalledGetAll() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group("AB-01"));
        expectedGroups.add(new Group("BA-12"));
        expectedGroups.add(new Group("AC-06"));

        for (Group Group : expectedGroups) {
            groupRepository.save(Group);
        }
        assertEquals(expectedGroups, groupRepository.findAll());
    }
}
