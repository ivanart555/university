package com.ivanart555.university.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;

@SpringJUnitConfig(TestSpringConfig.class)
class LessonDAOImplTest {
    private Environment env;
    private JdbcTemplate jdbcTemplate;
    private LessonDAO lessonDAO;

    private static ResourceDatabasePopulator sqlScript;

    private static final String CREATE_TABLES_SQL_SCRIPT = "scripts/create/tables.sql";
    private static final String CREATE_TEST_DATA_SQL_SCRIPT = "scripts/create/testData.sql";
    private static final String WIPE_TABLES_SQL_SCRIPT = "scripts/wipe/tables.sql";
    private static final String LESSON_ID = "lesson_id";
    private static final String COURSE_ID = "course_id";
    private static final String ROOM_ID = "room_id";
    private static final String LECTURER_ID = "lecturer_id";
    private static final String LESSON_START = "lesson_start";
    private static final String LESSON_END = "lesson_end";
    private static final String CONNECTION_ERROR = "Could not connect to database or SQL query error.";

    @Autowired
    private LessonDAOImplTest(LessonDAO lessonDAO, Environment env, JdbcTemplate jdbcTemplate) {
        this.lessonDAO = lessonDAO;
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

    void createTestData() {
        sqlScript = new ResourceDatabasePopulator();
        sqlScript.addScript(new ClassPathResource(CREATE_TEST_DATA_SQL_SCRIPT));
        DatabasePopulatorUtils.execute(sqlScript, jdbcTemplate.getDataSource());
    }

    @Test
    void shouldAddLessonToDatabase_whenCalledCreate() throws DAOException {
        Lesson expectedLesson = new Lesson(1, 3, 2, 2, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        lessonDAO.create(expectedLesson);

        String sql = env.getProperty("sql.lessons.get.byId");
        Lesson actualLesson = null;
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, expectedLesson.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer id = rs.getInt(LESSON_ID);
                    Integer courseId = rs.getInt(COURSE_ID);
                    Integer roomId = rs.getInt(ROOM_ID);
                    Integer lecturerId = rs.getInt(LECTURER_ID);
                    LocalDateTime lessonStart = rs.getTimestamp(LESSON_START).toLocalDateTime();
                    LocalDateTime lessonEnd = rs.getTimestamp(LESSON_END).toLocalDateTime();
                    actualLesson = new Lesson(id, courseId, roomId, lecturerId, lessonStart, lessonEnd);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }

        assertEquals(expectedLesson, actualLesson);
    }

    @Test
    void shouldReturnLessonFromDatabase_whenGivenId() throws DAOException {
        Lesson expectedLesson = new Lesson(1, 3, 2, 2, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        lessonDAO.create(expectedLesson);

        Lesson actualLesson = lessonDAO.getById(expectedLesson.getId());
        assertEquals(expectedLesson, actualLesson);
    }

    @Test
    void shouldReturnAllLessonsFromDatabase_whenCalledGetAll() throws DAOException {
        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(new Lesson(1, 3, 2, 2, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        expectedLessons.add(new Lesson(2, 1, 4, 1, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)));
        expectedLessons.add(new Lesson(3, 2, 3, 2, LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3)));

        for (Lesson lesson : expectedLessons) {
            lessonDAO.create(lesson);
        }
        assertEquals(expectedLessons, lessonDAO.getAll());
    }

    @Test
    void shouldUpdateLessonsDataAtDatabase_whenCalledUpdate() throws DAOException {
        Lesson expectedLesson = new Lesson(1, 3, 2, 2, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        lessonDAO.create(expectedLesson);

        expectedLesson.setCourseId(6);
        expectedLesson.setRoomId(2);
        expectedLesson.setLessonStart(LocalDateTime.now().plusHours(4));
        expectedLesson.setLessonEnd(LocalDateTime.now().plusHours(5));
        expectedLesson.setLecturerId(2);

        lessonDAO.update(expectedLesson);

        Lesson actualLesson = lessonDAO.getById(expectedLesson.getId());
        assertEquals(expectedLesson, actualLesson);
    }

    @Test
    void shouldDeleteLessonFromDatabase_whenGivenId() throws DAOException {
        Lesson lesson = new Lesson(1, 3, 2, 2, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        lessonDAO.create(lesson);

        lessonDAO.delete(lesson.getId());
        assertThrows(EntityNotFoundException.class, () -> lessonDAO.getById(lesson.getId()));
    }

    @Test
    void shouldAddLessonIdAndGroupIdToLessonsGroupsTable_whenGivenLessonIdAndGroupId() throws DAOException {
        createTestData();
        Lesson lesson = new Lesson(1, 3, 2, 2, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        lessonDAO.create(lesson);
        int expectedLessonId = 1;
        int expectedGroupId = 2;
        int actualLessonId = 0;
        int actualGroupd = 0;

        lessonDAO.assignLessonToGroup(expectedLessonId, expectedGroupId);

        String sql = "SELECT * FROM university.lessons_groups WHERE lesson_id =? AND group_id = ?";
        try (Connection con = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, expectedLessonId);
            ps.setInt(2, expectedGroupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    actualLessonId = rs.getInt("lesson_id");
                    actualGroupd = rs.getInt("group_id");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(CONNECTION_ERROR, e);
        }

        assertEquals(expectedLessonId, actualLessonId);
        assertEquals(expectedGroupId, actualGroupd);
    }

    @Test
    void shouldReturnLessons_whenGivenDateTimeIntervalAndGroupId() throws DAOException {
        createTestData();
        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(
                new Lesson(1, 3, 2, 2, LocalDateTime.of(2021, 5, 18, 10, 00), LocalDateTime.of(2021, 5, 18, 11, 00)));
        expectedLessons.add(
                new Lesson(2, 1, 4, 1, LocalDateTime.of(2021, 5, 18, 11, 00), LocalDateTime.of(2021, 5, 18, 12, 00)));
        expectedLessons.add(
                new Lesson(3, 2, 3, 2, LocalDateTime.of(2021, 5, 18, 12, 00), LocalDateTime.of(2021, 5, 18, 13, 00)));
        int groupId = 2;

        for (Lesson lesson : expectedLessons) {
            lessonDAO.create(lesson);
            lessonDAO.assignLessonToGroup(lesson.getId(), groupId);
        }

        List<Lesson> actualLessons = lessonDAO.getByDateTimeIntervalAndGroupId(groupId,
                LocalDateTime.of(2021, 5, 18, 10, 00), LocalDateTime.of(2021, 5, 18, 13, 00));

        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    void shouldReturnLessons_whenGivenDateTimeIntervalAndStudentId() throws DAOException {
        createTestData();
        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(
                new Lesson(1, 3, 2, 2, LocalDateTime.of(2021, 5, 18, 10, 00), LocalDateTime.of(2021, 5, 18, 11, 00)));
        expectedLessons.add(
                new Lesson(2, 1, 4, 1, LocalDateTime.of(2021, 5, 18, 11, 00), LocalDateTime.of(2021, 5, 18, 12, 00)));
        expectedLessons.add(
                new Lesson(3, 2, 3, 2, LocalDateTime.of(2021, 5, 18, 12, 00), LocalDateTime.of(2021, 5, 18, 13, 00)));
        int studentId = 2;

        for (Lesson lesson : expectedLessons) {
            lessonDAO.create(lesson);
        }

        List<Lesson> actualLessons = lessonDAO.getByDateTimeIntervalAndStudentId(studentId,
                LocalDateTime.of(2021, 5, 18, 10, 00), LocalDateTime.of(2021, 5, 18, 13, 00));

        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    void shouldReturnLessons_whenGivenDateTimeIntervalAndLecturerId() throws DAOException {
        createTestData();
        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(
                new Lesson(1, 3, 2, 2, LocalDateTime.of(2021, 5, 18, 10, 00), LocalDateTime.of(2021, 5, 18, 11, 00)));
        expectedLessons.add(
                new Lesson(2, 1, 4, 2, LocalDateTime.of(2021, 5, 18, 11, 00), LocalDateTime.of(2021, 5, 18, 12, 00)));
        expectedLessons.add(
                new Lesson(3, 2, 3, 2, LocalDateTime.of(2021, 5, 18, 12, 00), LocalDateTime.of(2021, 5, 18, 13, 00)));
        int lecturerId = 2;

        for (Lesson lesson : expectedLessons) {
            lessonDAO.create(lesson);
        }

        List<Lesson> actualLessons = lessonDAO.getByDateTimeIntervalAndLecturerId(lecturerId,
                LocalDateTime.of(2021, 5, 18, 10, 00), LocalDateTime.of(2021, 5, 18, 13, 00));

        assertEquals(expectedLessons, actualLessons);
    }
}
