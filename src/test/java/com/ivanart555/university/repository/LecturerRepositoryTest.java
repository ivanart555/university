package com.ivanart555.university.repository;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.repository.LecturerRepository;
import com.ivanart555.university.test_data.TestData;
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
class LecturerRepositoryTest {
    private LecturerRepository lecturerRepository;

    @Autowired
    private TestData testData;

    @Autowired
    private LecturerRepositoryTest(LecturerRepository lecturerRepository, Environment env, JdbcTemplate jdbcTemplate) {
        this.lecturerRepository = lecturerRepository;
    }

    @Test
    void shouldReturnAllLecturersFromDatabase_whenCalledGetAll() {
        List<Lecturer> expectedLecturers = testData.getTestLecturers();

        for (Lecturer lecturer : expectedLecturers) {
            lecturerRepository.save(lecturer);
        }
        assertEquals(expectedLecturers, lecturerRepository.findAll());
    }

    @Test
    void shouldReturnAllActiveLecturersFromDatabase_whenCalledGetAllActive() {
        List<Lecturer> expectedLecturers = new ArrayList<>();
        expectedLecturers.add(testData.getTestLecturers().get(0));
        expectedLecturers.add(testData.getTestLecturers().get(1));

        Lecturer notActiveLecturer = testData.getTestLecturers().get(2);
        notActiveLecturer.setActive(false);

        for (Lecturer lecturer : expectedLecturers) {
            lecturerRepository.save(lecturer);
        }

        lecturerRepository.save(notActiveLecturer);

        assertEquals(expectedLecturers, lecturerRepository.getAllActive());
    }
}
