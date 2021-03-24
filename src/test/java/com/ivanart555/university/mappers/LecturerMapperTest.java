package com.ivanart555.university.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Lecturer;

@SpringJUnitConfig(TestSpringConfig.class)
class LecturerMapperTest {

    @Autowired
    private LecturerMapper lecturerMapper;

    @Test
    void shouldReturnLecturer_WhenCalledMapRow() throws SQLException {
        Lecturer expectedLecturer = new Lecturer(1, "Alex", "Smith");

        ResultSet rsMock = mock(ResultSet.class);
        when(rsMock.getInt("lecturer_id")).thenReturn(1);
        when(rsMock.getString("lecturer_name")).thenReturn("Alex");
        when(rsMock.getString("lecturer_lastname")).thenReturn("Smith");
        when(rsMock.next()).thenReturn(true).thenReturn(false);

        assertEquals(expectedLecturer, lecturerMapper.mapRow(rsMock, 1));
    }
}
