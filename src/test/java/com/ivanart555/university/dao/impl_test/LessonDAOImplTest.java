package com.ivanart555.university.dao.impl_test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class LessonDAOImplTest {
    private LessonDAO lessonDAO;
    private CourseDAO courseDAO;
    private ClassroomDAO classroomDAO;
    private LecturerDAO lecturerDAO;
    private GroupDAO groupDAO;

    @Autowired
    public LessonDAOImplTest(LessonDAO lessonDAO, CourseDAO courseDAO, ClassroomDAO classroomDAO,
            LecturerDAO lecturerDAO, GroupDAO groupDAO) {
        this.lessonDAO = lessonDAO;
        this.courseDAO = courseDAO;
        this.classroomDAO = classroomDAO;
        this.lecturerDAO = lecturerDAO;
        this.groupDAO = groupDAO;
    }

    @Test
    void shouldReturnAllLessonsFromDatabase_whenCalledGetAll() throws DAOException {
        Course course = new Course("math", "");
        courseDAO.create(course);

        Classroom classroom = new Classroom("100");
        classroomDAO.create(classroom);

        Lecturer lecturer = new Lecturer("Alex", "Black");
        lecturerDAO.create(lecturer);

        Group group = new Group("AB-01");
        groupDAO.create(group);

        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(
                new Lesson(course, classroom, lecturer, group, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        expectedLessons.add(new Lesson(course, classroom, lecturer, group, LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2)));
        expectedLessons.add(new Lesson(course, classroom, lecturer, group, LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusHours(3)));

        for (Lesson lesson : expectedLessons) {
            lessonDAO.create(lesson);
        }
        assertEquals(expectedLessons, lessonDAO.getAll());
    }

    @Test
    void shouldReturnLessons_whenGivenDateTimeIntervalAndGroupId() throws DAOException {
        Course course = new Course("math", "");
        courseDAO.create(course);

        Classroom classroom = new Classroom("100");
        classroomDAO.create(classroom);

        Lecturer lecturer = new Lecturer("Alex", "Black");
        lecturerDAO.create(lecturer);

        Group group1 = new Group("AB-01");
        groupDAO.create(group1);

        Group group2 = new Group("AB-05");
        groupDAO.create(group2);

        Lesson lesson1 = new Lesson(course, classroom, lecturer, group1, LocalDateTime.of(2021, 5, 18, 10, 00),
                LocalDateTime.of(2021, 5, 18, 11, 00));
        Lesson lesson2 = new Lesson(course, classroom, lecturer, group1, LocalDateTime.of(2021, 5, 18, 11, 00),
                LocalDateTime.of(2021, 5, 18, 12, 00));
        Lesson lesson3 = new Lesson(course, classroom, lecturer, group2, LocalDateTime.of(2021, 5, 18, 12, 00),
                LocalDateTime.of(2021, 5, 18, 13, 00));
        lessonDAO.create(lesson1);
        lessonDAO.create(lesson2);
        lessonDAO.create(lesson3);

        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(lesson3);

        int groupId = 2;

        List<Lesson> actualLessons = lessonDAO.getByDateTimeIntervalAndGroupId(groupId,
                LocalDateTime.of(2021, 5, 18, 10, 00), LocalDateTime.of(2021, 5, 18, 13, 00));

        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    void shouldReturnLessons_whenGivenDateTimeIntervalAndLecturerId() throws DAOException {
        Course course = new Course("math", "");
        courseDAO.create(course);

        Classroom classroom = new Classroom("100");
        classroomDAO.create(classroom);

        Lecturer lecturer1 = new Lecturer("Alex", "Black");
        lecturerDAO.create(lecturer1);

        Lecturer lecturer2 = new Lecturer("Alex", "Black");
        lecturerDAO.create(lecturer2);

        Group group = new Group("AB-01");
        groupDAO.create(group);

        Lesson lesson1 = new Lesson(course, classroom, lecturer1, group, LocalDateTime.of(2021, 5, 18, 10, 00),
                LocalDateTime.of(2021, 5, 18, 11, 00));
        Lesson lesson2 = new Lesson(course, classroom, lecturer2, group, LocalDateTime.of(2021, 5, 18, 11, 00),
                LocalDateTime.of(2021, 5, 18, 12, 00));
        Lesson lesson3 = new Lesson(course, classroom, lecturer1, group, LocalDateTime.of(2021, 5, 18, 12, 00),
                LocalDateTime.of(2021, 5, 18, 13, 00));
        lessonDAO.create(lesson1);
        lessonDAO.create(lesson2);
        lessonDAO.create(lesson3);

        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(lesson2);

        int lecturerId = 2;

        List<Lesson> actualLessons = lessonDAO.getByDateTimeIntervalAndLecturerId(lecturerId,
                LocalDateTime.of(2021, 5, 18, 10, 00), LocalDateTime.of(2021, 5, 18, 13, 00));

        assertEquals(expectedLessons, actualLessons);
    }
}
