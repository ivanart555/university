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
import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.DAOException;

@SpringJUnitConfig(TestSpringConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
class LecturerDAOImplTest {
    private LecturerDAO lecturerDAO;

    @Autowired
    private LecturerDAOImplTest(LecturerDAO lecturerDAO, Environment env, JdbcTemplate jdbcTemplate) {
        this.lecturerDAO = lecturerDAO;
    }

    @Test
    void shouldReturnAllLecturersFromDatabase_whenCalledGetAll() throws DAOException {
        List<Lecturer> expectedLecturers = new ArrayList<>();
        expectedLecturers.add(new Lecturer("Alex", "Smith"));
        expectedLecturers.add(new Lecturer("Peter", "White"));
        expectedLecturers.add(new Lecturer("Mary", "Black"));

        for (Lecturer lecturer : expectedLecturers) {
            lecturerDAO.create(lecturer);
        }
        assertEquals(expectedLecturers, lecturerDAO.getAll());
    }

    @Test
    void shouldReturnAllActiveLecturersFromDatabase_whenCalledGetAllActive() throws DAOException {
        List<Lecturer> expectedLecturers = new ArrayList<>();
        expectedLecturers.add(new Lecturer("Alex", "Smith"));
        expectedLecturers.add(new Lecturer("Peter", "White"));
        
        Lecturer notActiveLecturer = new Lecturer("Mary", "Black");
        notActiveLecturer.setActive(false);
        
        for (Lecturer lecturer : expectedLecturers) {
            lecturerDAO.create(lecturer);
        }


        lecturerDAO.create(notActiveLecturer);

        assertEquals(expectedLecturers, lecturerDAO.getAllActive());
    }
}
