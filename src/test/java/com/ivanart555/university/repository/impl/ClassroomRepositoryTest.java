package com.ivanart555.university.repository.impl;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.repository.ClassroomRepository;
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
class ClassroomRepositoryImplTest {
    private ClassroomRepository classroomRepository;

    @Autowired
    private TestData testData;

    @Autowired
    private ClassroomRepositoryImplTest(ClassroomRepository classroomRepository, Environment env, JdbcTemplate jdbcTemplate) {
        this.classroomRepository = classroomRepository;
    }

    @Test
    void shouldReturnAllClassroomsFromDatabase_whenCalledGetAll() {
        List<Classroom> expectedClassrooms = testData.getTestClassrooms();

        for (Classroom classroom : expectedClassrooms) {
            classroomRepository.save(classroom);
        }
        assertEquals(expectedClassrooms, classroomRepository.findAll());
    }

}
