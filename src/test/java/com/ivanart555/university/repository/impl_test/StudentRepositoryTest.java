package com.ivanart555.university.repository.impl_test;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.repository.StudentRepository;
import com.ivanart555.university.test_data.TestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentRepositoryImplTest {
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    @Autowired
    private TestData testData;

    @Autowired
    private StudentRepositoryImplTest(StudentRepository studentRepository, GroupRepository groupRepository, Environment env,
                                      JdbcTemplate jdbcTemplate) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Test
    void shouldReturnAllStudentsFromDatabase_whenCalledGetAll() {
        List<Student> expectedStudents = testData.getTestStudents();

        for (Student Student : expectedStudents) {
            studentRepository.save(Student);
        }
        assertEquals(expectedStudents, studentRepository.findAll());
    }

    @Test
    void shouldReturnAllActiveStudentsFromDatabase_whenCalledGetAllActive() {
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(testData.getTestStudents().get(0));
        expectedStudents.add(testData.getTestStudents().get(1));

        Student notActiveStudent = testData.getTestStudents().get(2);
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
        Group group1 = new Group(1, "AF-01", null, null);
        Group group2 = new Group(2, "AF-02", null, null);
        groupRepository.save(group1);
        groupRepository.save(group2);

        expectedStudents.add(new Student(1, "Peter", "Jackson", true, group1));
        expectedStudents.add(new Student(2, "Alex", "Smith", true, group1));
        expectedStudents.add(new Student(3, "Ann", "White", true, group2));

        for (Student Student : expectedStudents) {
            studentRepository.save(Student);
        }

        expectedStudents.remove(2);

        List<Student> actualStudents = studentRepository.findByGroupId(1);
        assertEquals(expectedStudents, actualStudents);
    }
}
