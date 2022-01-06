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
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.DAOException;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class CourseDAOImplTest {
    private CourseDAO courseDAO;


    @Autowired
    private CourseDAOImplTest(CourseDAO courseDAO, Environment env, JdbcTemplate jdbcTemplate) {
        this.courseDAO = courseDAO;
    }

    @Test
    void shouldReturnAllCoursesFromDatabase_whenCalledGetAll() throws DAOException {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course("english", "English languadge learning."));
        expectedCourses.add(new Course("astronomy", "Astronomy is the study of the sun, moon, stars, planets."));
        expectedCourses.add(new Course("marketing", "Advertising, selling, and delivering products to consumers."));

        for (Course Course : expectedCourses) {
            courseDAO.create(Course);
        }
        assertEquals(expectedCourses, courseDAO.getAll());
    }
}
