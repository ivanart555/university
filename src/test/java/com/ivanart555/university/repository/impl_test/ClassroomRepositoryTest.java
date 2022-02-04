package com.ivanart555.university.repository.impl_test;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.repository.ClassroomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class ClassroomRepositoryImplTest {
    private ClassroomRepository classroomRepository;

    @Autowired
    private ClassroomRepositoryImplTest(ClassroomRepository classroomRepository, Environment env, JdbcTemplate jdbcTemplate) {
        this.classroomRepository = classroomRepository;
    }

    @Test
    void shouldReturnAllClassroomsFromDatabase_whenCalledGetAll() {
        List<Classroom> expectedClassrooms = new ArrayList<>();
        expectedClassrooms.add(new Classroom("100"));
        expectedClassrooms.add(new Classroom("101"));
        expectedClassrooms.add(new Classroom("102"));

        for (Classroom classroom : expectedClassrooms) {
            classroomRepository.save(classroom);
        }
        assertEquals(expectedClassrooms, classroomRepository.findAll());
    }

}
