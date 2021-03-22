package com.ivanart555.university.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Student;

@SpringJUnitConfig(TestSpringConfig.class)
class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void shouldReturnGroup_WhenCalledMapRow() throws SQLException {
        Student expectedStudent = new Student(1, "Peter", "Jackson", 4);

        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getInt("student_id")).thenReturn(1);
        Mockito.when(rsMock.getString("student_name")).thenReturn("Peter");
        Mockito.when(rsMock.getString("student_lastname")).thenReturn("Jackson");
        Mockito.when(rsMock.getInt("group_id")).thenReturn(4);
        Mockito.when(rsMock.next()).thenReturn(true).thenReturn(false);

        assertEquals(expectedStudent, studentMapper.mapRow(rsMock, 1));
    }
}
