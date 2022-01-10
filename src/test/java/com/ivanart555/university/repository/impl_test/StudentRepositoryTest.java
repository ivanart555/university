package com.ivanart555.university.repository.impl_test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.repository.StudentRepository;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentRepositoryImplTest {
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    @Autowired
    private StudentRepositoryImplTest(StudentRepository studentRepository, GroupRepository groupRepository, Environment env,
            JdbcTemplate jdbcTemplate) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Test
    void shouldReturnAllStudentsFromDatabase_whenCalledGetAll() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("Peter", "Jackson"));
        expectedStudents.add(new Student("Alex", "Smith"));
        expectedStudents.add(new Student("Ann", "White"));

        for (Student Student : expectedStudents) {
            studentRepository.save(Student);
        }
        assertEquals(expectedStudents, studentRepository.findAll());
    }

    @Test
    void shouldReturnAllActiveStudentsFromDatabase_whenCalledGetAllActive() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("Peter", "Jackson"));
        expectedStudents.add(new Student("Alex", "Smith"));

        Student notActiveStudent = new Student("Ann", "White");
        notActiveStudent.setActive(false);

        for (Student Student : expectedStudents) {
            studentRepository.save(Student);
        }
        studentRepository.save(notActiveStudent);

        assertEquals(expectedStudents, studentRepository.getAllActive());
    }

    @Test
    void shouldReturnStudentsFromDatabase_whenGivenGroupId() {
        List<Student> expectedStudents = new ArrayList<>();
        Group group1 = new Group("AF-01");
        Group group2 = new Group("AF-02");
        groupRepository.save(group1);
        groupRepository.save(group2);

        expectedStudents.add(new Student("Peter", "Jackson", group1));
        expectedStudents.add(new Student("Alex", "Smith", group1));
        expectedStudents.add(new Student("Ann", "White", group2));

        for (Student Student : expectedStudents) {
            studentRepository.save(Student);
        }

        expectedStudents.remove(2);

        List<Student> actualStudents = studentRepository.findByGroupId(1);
        assertEquals(expectedStudents, actualStudents);
    }
}
