package com.ivanart555.university.repository.impl;

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
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ClassroomRepository classroomRepository;
    private final LecturerRepository lecturerRepository;
    private final GroupRepository groupRepository;

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
        Course course = new Course(1, "math", "", null, null, null);
        courseRepository.save(course);

        Classroom classroom = new Classroom(1, "100");
        classroomRepository.save(classroom);

        Lecturer lecturer = new Lecturer(1, "Alex", "Black", true, null);
        lecturerRepository.save(lecturer);

        Group group = new Group(1, "AB-01", null, null);
        groupRepository.save(group);

        List<Lesson> expectedLessons = new ArrayList<>();
        expectedLessons.add(
                new Lesson(1, course, classroom, lecturer, group, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        expectedLessons.add(new Lesson(2, course, classroom, lecturer, group, LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2)));
        expectedLessons.add(new Lesson(3, course, classroom, lecturer, group, LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusHours(3)));

        for (Lesson lesson : expectedLessons) {
            lessonRepository.save(lesson);
        }
        assertEquals(expectedLessons, lessonRepository.findAll());
    }

    @Test
    void shouldReturnLesson_whenGivenDateTimeIntervalAndGroupId() {
        Course course = new Course(1, "math", "", null, null, null);
        courseRepository.save(course);

        Classroom classroom = new Classroom(1, "100");
        classroomRepository.save(classroom);

        Lecturer lecturer = new Lecturer(1, "Alex", "Black", true, null);
        lecturerRepository.save(lecturer);

        Group group1 = new Group(1, "AB-01", null, null);
        groupRepository.save(group1);

        Group group2 = new Group(2, "AB-05", null, null);
        groupRepository.save(group2);

        Lesson lesson1 = new Lesson(1, course, classroom, lecturer, group1, LocalDateTime.of(2021, 5, 18, 10, 00),
                LocalDateTime.of(2021, 5, 18, 11, 00));
        Lesson lesson2 = new Lesson(2, course, classroom, lecturer, group1, LocalDateTime.of(2021, 5, 18, 11, 00),
                LocalDateTime.of(2021, 5, 18, 12, 00));
        Lesson lesson3 = new Lesson(3, course, classroom, lecturer, group2, LocalDateTime.of(2021, 5, 18, 12, 00),
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
        Course course = new Course(1, "math", "", null, null, null);
        courseRepository.save(course);

        Classroom classroom = new Classroom(1, "100");
        classroomRepository.save(classroom);

        Lecturer lecturer1 = new Lecturer(1, "Alex", "Black", true, null);
        lecturerRepository.save(lecturer1);

        Lecturer lecturer2 = new Lecturer(2, "Alex", "Black", true, null);
        lecturerRepository.save(lecturer2);

        Group group = new Group(1, "AB-01", null, null);
        groupRepository.save(group);

        Lesson lesson1 = new Lesson(1, course, classroom, lecturer1, group, LocalDateTime.of(2021, 5, 18, 10, 00),
                LocalDateTime.of(2021, 5, 18, 11, 00));
        Lesson lesson2 = new Lesson(2, course, classroom, lecturer2, group, LocalDateTime.of(2021, 5, 18, 11, 00),
                LocalDateTime.of(2021, 5, 18, 12, 00));
        Lesson lesson3 = new Lesson(3, course, classroom, lecturer1, group, LocalDateTime.of(2021, 5, 18, 12, 00),
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
