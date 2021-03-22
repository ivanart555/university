package com.ivanart555.university.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Course;

@SpringJUnitConfig(TestSpringConfig.class)
class CourseMapperTest {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    void shouldReturnCourse_WhenCalledMapRow() throws SQLException {
        Course expectedCourse = new Course(1, "english", "English languadge learning.");

        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getInt("course_id")).thenReturn(1);
        Mockito.when(rsMock.getString("course_name")).thenReturn("english");
        Mockito.when(rsMock.getString("course_description")).thenReturn("English languadge learning.");
        Mockito.when(rsMock.next()).thenReturn(true).thenReturn(false);

        assertEquals(expectedCourse, courseMapper.mapRow(rsMock, 1));
    }
}
