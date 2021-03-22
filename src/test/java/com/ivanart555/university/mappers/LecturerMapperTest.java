package com.ivanart555.university.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Lecturer;

@SpringJUnitConfig(TestSpringConfig.class)
class LecturerMapperTest {

    @Autowired
    private LecturerMapper lecturerMapper;

    @Test
    void shouldReturnGroup_WhenCalledMapRow() throws SQLException {
        Lecturer expectedLecturer = new Lecturer(1, "Alex", "Smith");

        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getInt("lecturer_id")).thenReturn(1);
        Mockito.when(rsMock.getString("lecturer_name")).thenReturn("Alex");
        Mockito.when(rsMock.getString("lecturer_lastname")).thenReturn("Smith");
        Mockito.when(rsMock.next()).thenReturn(true).thenReturn(false);

        assertEquals(expectedLecturer, lecturerMapper.mapRow(rsMock, 1));
    }
}
