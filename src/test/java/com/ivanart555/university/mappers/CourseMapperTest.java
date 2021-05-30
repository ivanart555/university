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
import com.ivanart555.university.entities.Course;

@SpringJUnitConfig(TestSpringConfig.class)
class CourseMapperTest {
    private CourseMapper courseMapper;

    @Autowired
    private CourseMapperTest(CourseMapper courseMapper){
        this.courseMapper = courseMapper;
    }
    
    @Test
    void shouldReturnCourse_WhenCalledMapRow() throws SQLException {
        Course expectedCourse = new Course(1, "english", "English languadge learning.");

        ResultSet rsMock = mock(ResultSet.class);
        when(rsMock.getInt("course_id")).thenReturn(1);
        when(rsMock.getString("course_name")).thenReturn("english");
        when(rsMock.getString("course_description")).thenReturn("English languadge learning.");
        when(rsMock.next()).thenReturn(true).thenReturn(false);

        assertEquals(expectedCourse, courseMapper.mapRow(rsMock, 1));
    }
}
