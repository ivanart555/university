package com.ivanart555.university.dao.impl_test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.DAOException;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class ClassroomDAOImplTest {
    private ClassroomDAO classroomDAO;

    @Autowired
    private ClassroomDAOImplTest(ClassroomDAO classroomDAO, Environment env, JdbcTemplate jdbcTemplate) {
        this.classroomDAO = classroomDAO;
    }

    @Test
    void shouldReturnAllClassroomsFromDatabase_whenCalledGetAll() throws DAOException {
        List<Classroom> expectedClassrooms = new ArrayList<>();
        expectedClassrooms.add(new Classroom("100"));
        expectedClassrooms.add(new Classroom("100A"));
        expectedClassrooms.add(new Classroom("100B"));

        for (Classroom classroom : expectedClassrooms) {
            classroomDAO.create(classroom);
        }
        assertEquals(expectedClassrooms, classroomDAO.getAll());
    }

}
