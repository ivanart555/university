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
import com.ivanart555.university.entities.Student;

@SpringJUnitConfig(TestSpringConfig.class)
class StudentMapperTest {
    private StudentMapper studentMapper;

    @Autowired
    private StudentMapperTest(StudentMapper studentMapper){
        this.studentMapper = studentMapper;
    }

    @Test
    void shouldReturnStudent_WhenCalledMapRow() throws SQLException {
        Student expectedStudent = new Student(1, "Peter", "Jackson", 4);

        ResultSet rsMock = mock(ResultSet.class);
        when(rsMock.getInt("student_id")).thenReturn(1);
        when(rsMock.getString("student_name")).thenReturn("Peter");
        when(rsMock.getString("student_lastname")).thenReturn("Jackson");
        when(rsMock.getInt("group_id")).thenReturn(4);
        when(rsMock.next()).thenReturn(true).thenReturn(false);

        assertEquals(expectedStudent, studentMapper.mapRow(rsMock, 1));
    }
}
