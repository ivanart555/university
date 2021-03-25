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
import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.DAOException;

@SpringJUnitConfig(TestSpringConfig.class)
class LecturerDAOImplTest {

    @Autowired
    private Environment env;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LecturerDAO lecturerDAO;

    private static ResourceDatabasePopulator sqlScript;

    private static final String CREATE_TABLES_SQL_SCRIPT = "scripts/create/tables.sql";
    private static final String WIPE_TABLES_SQL_SCRIPT = "scripts/wipe/tables.sql";
    private static final String LECTURER_ID = "lecturer_id";
    private static final String LECTURER_FIRSTNAME = "lecturer_name";
    private static final String LECTURER_LASTNAME = "lecturer_lastname";
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
    void shouldAddLecturerToDatabase_whenCalledCreate() throws DAOException {
        Lecturer expectedLecturer = new Lecturer(1, "Alex", "Smith");
        lecturerDAO.create(expectedLecturer);

        String sql = env.getProperty("sql.lecturers.get.byId");
        Lecturer actualLecturer = null;
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, expectedLecturer.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt(LECTURER_ID);
                    String lecturerName = rs.getString(LECTURER_FIRSTNAME);
                    String lecturerLastname = rs.getString(LECTURER_LASTNAME);
                    actualLecturer = new Lecturer(id, lecturerName, lecturerLastname);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }

        assertEquals(expectedLecturer, actualLecturer);
    }

    @Test
    void shouldReturnLecturerFromDatabase_whenGivenId() throws DAOException {
        Lecturer expectedLecturer = new Lecturer(1, "Alex", "Smith");
        lecturerDAO.create(expectedLecturer);

        Lecturer actualLecturer = lecturerDAO.getById(expectedLecturer.getId());
        assertEquals(expectedLecturer, actualLecturer);
    }

    @Test
    void shouldReturnAllLecturersFromDatabase_whenCalledGetAll() throws DAOException {
        List<Lecturer> expectedLecturers = new ArrayList<>();
        expectedLecturers.add(new Lecturer(1, "Alex", "Smith"));
        expectedLecturers.add(new Lecturer(2, "Peter", "White"));
        expectedLecturers.add(new Lecturer(3, "Mary", "Black"));

        for (Lecturer lecturer : expectedLecturers) {
            lecturerDAO.create(lecturer);
        }
        assertEquals(expectedLecturers, lecturerDAO.getAll());
    }

    @Test
    void shouldUpdateLecturersDataAtDatabase_whenCalledUpdate() throws DAOException {
        Lecturer expectedLecturer = new Lecturer(1, "Alex", "Smith");
        lecturerDAO.create(expectedLecturer);

        expectedLecturer.setFirstName("Linda");
        expectedLecturer.setFirstName("Flax");
        
        lecturerDAO.update(expectedLecturer);

        Lecturer actualLecturer = lecturerDAO.getById(expectedLecturer.getId());
        assertEquals(expectedLecturer, actualLecturer);
    }

    @Test
    void shouldDeleteLecturerFromDatabase_whenGivenId() throws DAOException {
        Lecturer lecturer = new Lecturer(1, "Alex", "Smith");
        lecturerDAO.create(lecturer);

        lecturerDAO.delete(lecturer.getId());
        assertNull(lecturerDAO.getById(lecturer.getId()));
    }
}
