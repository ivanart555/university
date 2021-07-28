package com.ivanart555.university.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;

@SpringJUnitConfig(TestSpringConfig.class)
class ClassroomDAOImplTest {
    private Environment env;
    private JdbcTemplate jdbcTemplate;
    private ClassroomDAO classroomDAO;

    private static ResourceDatabasePopulator sqlScript;

    private static final String CREATE_TABLES_SQL_SCRIPT = "scripts/create/tables.sql";
    private static final String WIPE_TABLES_SQL_SCRIPT = "scripts/wipe/tables.sql";
    private static final String CLASSROOM_ID = "room_id";
    private static final String CLASSROOM_NAME = "room_name";
    private static final String CONNECTION_ERROR = "Could not connect to database or SQL query error.";

    @Autowired
    private ClassroomDAOImplTest(ClassroomDAO classroomDAO, Environment env, JdbcTemplate jdbcTemplate) {
        this.classroomDAO = classroomDAO;
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
    void shouldAddClassroomToDatabase_whenCalledCreate() throws DAOException {
        Classroom expectedClassroom = new Classroom(1, "100");
        classroomDAO.create(expectedClassroom);

        String sql = env.getProperty("sql.classrooms.get.byId");
        Classroom actualClassroom = null;
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, expectedClassroom.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt(CLASSROOM_ID);
                    String classroomName = rs.getString(CLASSROOM_NAME);
                    actualClassroom = new Classroom(id, classroomName);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }

        assertEquals(expectedClassroom, actualClassroom);
    }

    @Test
    void shouldReturnClassroomFromDatabase_whenGivenId() throws DAOException {
        Classroom expectedClassroom = new Classroom(1, "100");
        classroomDAO.create(expectedClassroom);

        Classroom actualClassroom = classroomDAO.getById(expectedClassroom.getId());
        assertEquals(expectedClassroom, actualClassroom);
    }

    @Test
    void shouldReturnAllClassroomsFromDatabase_whenCalledGetAll() throws DAOException {
        List<Classroom> expectedClassrooms = new ArrayList<>();
        expectedClassrooms.add(new Classroom(1, "100"));
        expectedClassrooms.add(new Classroom(2, "100A"));
        expectedClassrooms.add(new Classroom(3, "100B"));

        for (Classroom classroom : expectedClassrooms) {
            classroomDAO.create(classroom);
        }
        assertEquals(expectedClassrooms, classroomDAO.getAll());
    }

    @Test
    void shouldUpdateClassroomsDataAtDatabase_whenCalledUpdate() throws DAOException {
        Classroom expectedClassroom = new Classroom(1, "100");
        classroomDAO.create(expectedClassroom);

        expectedClassroom.setName("116");

        classroomDAO.update(expectedClassroom);

        Classroom actualClassroom = classroomDAO.getById(expectedClassroom.getId());
        assertEquals(expectedClassroom, actualClassroom);
    }

    @Test
    void shouldDeleteClassroomFromDatabase_whenGivenId() throws DAOException {
        Classroom classroom = new Classroom(1, "100");
        classroomDAO.create(classroom);

        classroomDAO.delete(classroom.getId());

        assertThrows(EntityNotFoundException.class, () -> classroomDAO.getById(classroom.getId()));
    }
}
