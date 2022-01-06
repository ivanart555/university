package com.ivanart555.university.dao.impl_test;

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
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.DAOException;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentDAOImplTest {
    private StudentDAO studentDAO;
    private GroupDAO groupDAO;

    @Autowired
    private StudentDAOImplTest(StudentDAO studentDAO, GroupDAO groupDAO, Environment env, JdbcTemplate jdbcTemplate) {
        this.studentDAO = studentDAO;
        this.groupDAO = groupDAO;
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
    void shouldReturnStudentsFromDatabase_whenGivenGroupId() throws DAOException {
        List<Student> expectedStudents = new ArrayList<>();
        Group group1 = new Group("AF-01");
        Group group2 = new Group("AF-02");
        groupDAO.create(group1);
        groupDAO.create(group2);

        expectedStudents.add(new Student("Peter", "Jackson", group1));
        expectedStudents.add(new Student("Alex", "Smith", group1));
        expectedStudents.add(new Student("Ann", "White", group2));

        for (Student Student : expectedStudents) {
            studentDAO.create(Student);
        }

        expectedStudents.remove(2);

        List<Student> actualStudents = studentDAO.getStudentsByGroupId(1);
        assertEquals(expectedStudents, actualStudents);
    }
}
