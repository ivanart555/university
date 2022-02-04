package com.ivanart555.university.repository.impl_test;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.*;
import com.ivanart555.university.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class LessonRepositoryTest {
    private LessonRepository lessonRepository;
    private CourseRepository courseRepository;
    private ClassroomRepository classroomRepository;
    private LecturerRepository lecturerRepository;
    private GroupRepository groupRepository;

    @Autowired
    public LessonRepositoryTest(LessonRepository lessonRepository, CourseRepository courseRepository,
                                ClassroomRepository classroomRepository,
                                LecturerRepository lecturerRepository, GroupRepository groupRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.classroomRepository = classroomRepository;
        this.lecturerRepository = lecturerRepository;
        this.groupRepository = groupRepository;
    }

    @Test
    void shouldReturnAllLessonsFromDatabase_whenCalledGetAll() {
        Course course = new Course("math", "");
        courseRepository.save(course);

        Classroom classroom = new Classroom("100");
        classroomRepository.save(classroom);

        Lecturer lecturer = new Lecturer("Alex", "Black");
        lecturerRepository.save(lecturer);

        Group group = new Group("AB-01");
        groupRepository.save(group);

        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(
                new Lesson(course, classroom, lecturer, group, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        expectedLessons.add(new Lesson(course, classroom, lecturer, group, LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2)));
        expectedLessons.add(new Lesson(course, classroom, lecturer, group, LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusHours(3)));

        for (Lesson lesson : expectedLessons) {
            lessonRepository.save(lesson);
        }
        assertEquals(expectedLessons, lessonRepository.findAll());
    }

    @Test
    void shouldReturnLesson_whenGivenDateTimeIntervalAndGroupId() {
        Course course = new Course("math", "");
        courseRepository.save(course);

        Classroom classroom = new Classroom("100");
        classroomRepository.save(classroom);

        Lecturer lecturer = new Lecturer("Alex", "Black");
        lecturerRepository.save(lecturer);

        Group group1 = new Group("AB-01");
        groupRepository.save(group1);

        Group group2 = new Group("AB-05");
        groupRepository.save(group2);

        Lesson lesson1 = new Lesson(course, classroom, lecturer, group1, LocalDateTime.of(2021, 5, 18, 10, 00),
                LocalDateTime.of(2021, 5, 18, 11, 00));
        Lesson lesson2 = new Lesson(course, classroom, lecturer, group1, LocalDateTime.of(2021, 5, 18, 11, 00),
                LocalDateTime.of(2021, 5, 18, 12, 00));
        Lesson lesson3 = new Lesson(course, classroom, lecturer, group2, LocalDateTime.of(2021, 5, 18, 12, 00),
                LocalDateTime.of(2021, 5, 18, 13, 00));
        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);
        lessonRepository.save(lesson3);

        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(lesson3);

        int groupId = 2;

        List<Lesson> actualLessons = lessonRepository
                .findAllByGroupIdAndLessonStartLessThanEqualAndLessonEndGreaterThanEqual(groupId,
                        LocalDateTime.of(2021, 5, 18, 13, 00),
                        LocalDateTime.of(2021, 5, 18, 10, 00));

        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    void shouldReturnLessons_whenGivenDateTimeIntervalAndLecturerId() {
        Course course = new Course("math", "");
        courseRepository.save(course);

        Classroom classroom = new Classroom("100");
        classroomRepository.save(classroom);

        Lecturer lecturer1 = new Lecturer("Alex", "Black");
        lecturerRepository.save(lecturer1);

        Lecturer lecturer2 = new Lecturer("Alex", "Black");
        lecturerRepository.save(lecturer2);

        Group group = new Group("AB-01");
        groupRepository.save(group);

        Lesson lesson1 = new Lesson(course, classroom, lecturer1, group, LocalDateTime.of(2021, 5, 18, 10, 00),
                LocalDateTime.of(2021, 5, 18, 11, 00));
        Lesson lesson2 = new Lesson(course, classroom, lecturer2, group, LocalDateTime.of(2021, 5, 18, 11, 00),
                LocalDateTime.of(2021, 5, 18, 12, 00));
        Lesson lesson3 = new Lesson(course, classroom, lecturer1, group, LocalDateTime.of(2021, 5, 18, 12, 00),
                LocalDateTime.of(2021, 5, 18, 13, 00));
        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);
        lessonRepository.save(lesson3);

        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(lesson2);

        int lecturerId = 2;

        List<Lesson> actualLessons = lessonRepository
                .findAllByLecturerIdAndLessonStartLessThanEqualAndLessonEndGreaterThanEqual(lecturerId,
                        LocalDateTime.of(2021, 5, 18, 13, 00), LocalDateTime.of(2021, 5, 18, 10, 00));

        assertEquals(expectedLessons, actualLessons);
    }
}
