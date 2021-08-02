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
import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;

@SpringJUnitConfig(TestSpringConfig.class)
class StudentDAOImplTest {
    private Environment env;
    private JdbcTemplate jdbcTemplate;
    private StudentDAO studentDAO;

    private static ResourceDatabasePopulator sqlScript;

    private static final String CREATE_TABLES_SQL_SCRIPT = "scripts/create/tables.sql";
    private static final String CREATE_TEST_DATA_SQL_SCRIPT = "scripts/create/testData.sql";
    private static final String WIPE_TABLES_SQL_SCRIPT = "scripts/wipe/tables.sql";
    private static final String STUDENT_ID = "student_id";
    private static final String STUDENT_FIRSTNAME = "student_name";
    private static final String STUDENT_LASTNAME = "student_lastname";
    private static final String GROUP_ID = "group_id";
    private static final String ACTIVE = "active";
    private static final String CONNECTION_ERROR = "Could not connect to database or SQL query error.";

    @Autowired
    private StudentDAOImplTest(StudentDAO studentDAO, Environment env, JdbcTemplate jdbcTemplate) {
        this.studentDAO = studentDAO;
        this.env = env;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void createTables() {
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

    void createTestData() {
        sqlScript = new ResourceDatabasePopulator();
        sqlScript.addScript(new ClassPathResource(CREATE_TEST_DATA_SQL_SCRIPT));
        DatabasePopulatorUtils.execute(sqlScript, jdbcTemplate.getDataSource());
    }

    @Test
    void shouldAddStudentToDatabase_whenCalledCreate() throws DAOException {
        Student expectedStudent = new Student("Peter", "Jackson");
        studentDAO.create(expectedStudent);

        String sql = env.getProperty("sql.students.get.byId");
        Student actualStudent = null;
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, expectedStudent.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt(STUDENT_ID);
                    String studentName = rs.getString(STUDENT_FIRSTNAME);
                    String studentLastname = rs.getString(STUDENT_LASTNAME);
                    Integer groupId = rs.getInt(GROUP_ID);
                    Boolean active = rs.getBoolean(ACTIVE);
                    actualStudent = new Student(id, studentName, studentLastname, groupId, active);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldReturnStudentFromDatabase_whenGivenId() throws DAOException {
        Student expectedStudent = new Student("Peter", "Jackson");
        studentDAO.create(expectedStudent);

        Student actualStudent = studentDAO.getById(expectedStudent.getId());
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldReturnAllStudentsFromDatabase_whenCalledGetAll() throws DAOException {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("Peter", "Jackson"));
        expectedStudents.add(new Student("Alex", "Smith"));
        expectedStudents.add(new Student("Ann", "White"));

        for (Student Student : expectedStudents) {
            studentDAO.create(Student);
        }
        assertEquals(expectedStudents, studentDAO.getAll());
    }

    @Test
    void shouldReturnAllActiveStudentsFromDatabase_whenCalledGetAllActive() throws DAOException {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("Peter", "Jackson"));
        expectedStudents.add(new Student("Alex", "Smith"));

        Student notActiveStudent = new Student("Ann", "White");
        notActiveStudent.setActive(false);

        for (Student Student : expectedStudents) {
            studentDAO.create(Student);
        }
        studentDAO.create(notActiveStudent);

        assertEquals(expectedStudents, studentDAO.getAllActive());
    }

    @Test
    void shouldUpdateStudentsDataAtDatabase_whenCalledUpdate() throws DAOException {
        Student expectedStudent = new Student(1, "Peter", "Jackson", 4);
        studentDAO.create(expectedStudent);

        expectedStudent.setFirstName("Matt");
        expectedStudent.setLastName("Grave");
        expectedStudent.setGroupId(4);

        studentDAO.update(expectedStudent);

        Student actualStudent = studentDAO.getById(expectedStudent.getId());
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void shouldDeleteStudentFromDatabase_whenGivenId() throws DAOException {
        Student student = new Student(1, "Peter", "Jackson", 4);
        studentDAO.create(student);

        studentDAO.delete(student.getId());
        assertThrows(EntityNotFoundException.class, () -> studentDAO.getById(student.getId()));
    }

    @Test
    void shouldAddStudentIdAndCourseIdToStudentsCoursesTable_whenGivenStudentIdAndCourseId() throws DAOException {
        createTestData();
        int expectedStudentId = 1;
        int expectedCourseId = 2;
        int actualStudentId = 0;
        int actualCourseId = 0;

        studentDAO.addStudentToCourse(expectedStudentId, expectedCourseId);

        String sql = "SELECT * FROM university.students_courses WHERE student_id =? AND course_id = ?";
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, expectedStudentId);
            ps.setInt(2, expectedCourseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    actualStudentId = rs.getInt("student_id");
                    actualCourseId = rs.getInt("course_id");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }

        assertEquals(expectedStudentId, actualStudentId);
        assertEquals(expectedCourseId, actualCourseId);
    }

    @Test
    void shouldReturnStudentsFromDatabase_whenGivenGroupId() throws DAOException {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student(1, "Peter", "Jackson", 1));
        expectedStudents.add(new Student(2, "Alex", "Smith", 1));
        expectedStudents.add(new Student(3, "Ann", "White", 2));

        for (Student Student : expectedStudents) {
            studentDAO.create(Student);
        }
        for (Student Student : expectedStudents) {
            studentDAO.update(Student);
        }
        expectedStudents.remove(2);

        List<Student> actualStudents = studentDAO.getStudentsByGroupId(1);
        assertEquals(expectedStudents, actualStudents);
    }
}
