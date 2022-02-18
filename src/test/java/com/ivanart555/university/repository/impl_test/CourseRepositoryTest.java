package com.ivanart555.university.repository.impl_test;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.repository.CourseRepository;
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
class CourseRepositoryImplTest {
    private final CourseRepository courseRepository;

    @Autowired
    private TestData testData;

    @Autowired
    private CourseRepositoryImplTest(CourseRepository courseRepository, Environment env, JdbcTemplate jdbcTemplate) {
        this.courseRepository = courseRepository;
    }

    @Test
    void shouldReturnAllCoursesFromDatabase_whenCalledGetAll() {
        List<Course> expectedCourses = testData.getTestCourses();
        for (Course Course : expectedCourses) {
            courseRepository.save(Course);
        }
        assertEquals(expectedCourses, courseRepository.findAll());
    }
}
