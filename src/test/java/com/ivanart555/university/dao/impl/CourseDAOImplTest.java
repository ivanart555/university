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
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.DAOException;

@SpringJUnitConfig(TestSpringConfig.class)
class CourseDAOImplTest {
    private Environment env;
    private JdbcTemplate jdbcTemplate;
    private CourseDAO courseDAO;

    private static ResourceDatabasePopulator sqlScript;

    private static final String CREATE_TABLES_SQL_SCRIPT = "scripts/create/tables.sql";
    private static final String WIPE_TABLES_SQL_SCRIPT = "scripts/wipe/tables.sql";
    private static final String COURSE_ID = "course_id";
    private static final String COURSE_NAME = "course_name";
    private static final String COURSE_DESCRIPTION = "course_description";
    private static final String CONNECTION_ERROR = "Could not connect to database or SQL query error.";

    @Autowired
    private CourseDAOImplTest(CourseDAO courseDAO, Environment env, JdbcTemplate jdbcTemplate) {
        this.courseDAO = courseDAO;
        this.env = env;
        this.jdbcTemplate = jdbcTemplate;
    }
    
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
    void shouldAddCourseToDatabase_whenCalledCreate() throws DAOException {
        Course expectedCourse = new Course(1, "english", "English languadge learning.");
        courseDAO.create(expectedCourse);

        String sql = env.getProperty("sql.courses.get.byId");
        Course actualCourse = null;
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, expectedCourse.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt(COURSE_ID);
                    String CourseName = rs.getString(COURSE_NAME);
                    String CourseDescription = rs.getString(COURSE_DESCRIPTION);
                    actualCourse = new Course(id, CourseName, CourseDescription);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void shouldReturnCourseFromDatabase_whenGivenId() throws DAOException {
        Course expectedCourse = new Course(1, "english", "English languadge learning.");
        courseDAO.create(expectedCourse);

        Course actualCourse = courseDAO.getById(expectedCourse.getId());
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void shouldReturnAllCoursesFromDatabase_whenCalledGetAll() throws DAOException {
        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(new Course(1, "english", "English languadge learning."));
        expectedCourses.add(new Course(2, "astronomy", "Astronomy is the study of the sun, moon, stars, planets."));
        expectedCourses.add(new Course(3, "marketing", "Advertising, selling, and delivering products to consumers."));

        for (Course Course : expectedCourses) {
            courseDAO.create(Course);
        }
        assertEquals(expectedCourses, courseDAO.getAll());
    }

    @Test
    void shouldUpdateCoursesDataAtDatabase_whenCalledUpdate() throws DAOException {
        Course expectedCourse = new Course(1, "english", "English languadge learning.");
        courseDAO.create(expectedCourse);

        expectedCourse.setName("astronomy");
        expectedCourse.setDescription("Astronomy is the study of the sun, moon, stars, planets.");

        courseDAO.update(expectedCourse);

        Course actualCourse = courseDAO.getById(expectedCourse.getId());
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void shouldDeleteCourseFromDatabase_whenGivenId() throws DAOException {
        Course Course = new Course(1, "english", "English languadge learning.");
        courseDAO.create(Course);

        courseDAO.delete(Course.getId());
        assertNull(courseDAO.getById(Course.getId()));
    }
}
