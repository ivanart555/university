package com.ivanart555.university.repository.impl;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.test_data.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class GroupRepositoryImplTest {
    private final GroupRepository groupRepository;

    @Autowired
    private TestData testData;

    @Autowired
    private GroupRepositoryImplTest(GroupRepository groupRepository, Environment env, JdbcTemplate jdbcTemplate) {
        this.groupRepository = groupRepository;
    }

    @Test
    void shouldReturnAllGroupsFromDatabase_whenCalledGetAll() {
        List<Group> expectedGroups = testData.getTestGroups();

        for (Group Group : expectedGroups) {
            groupRepository.save(Group);
        }
        assertEquals(expectedGroups, groupRepository.findAll());
    }
}
