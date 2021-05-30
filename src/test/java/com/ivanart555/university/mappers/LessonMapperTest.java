package com.ivanart555.university.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Lesson;

@SpringJUnitConfig(TestSpringConfig.class)
class LessonMapperTest {
    private LessonMapper lessonMapper;

    @Autowired
    private LessonMapperTest(LessonMapper lessonMapper){
        this.lessonMapper = lessonMapper;
    }
    
    @Test
    void shouldReturnLesson_WhenCalledMapRow() throws SQLException {
        LocalDateTime lessonStart = LocalDateTime.now();
        LocalDateTime lessonEnd = LocalDateTime.now().plusHours(1);

        Lesson expectedLesson = new Lesson(1, 3, 2, 2, lessonStart, lessonEnd);

        ResultSet rsMock = mock(ResultSet.class);
        when(rsMock.getInt("lesson_id")).thenReturn(1);
        when(rsMock.getInt("course_id")).thenReturn(3);
        when(rsMock.getInt("room_id")).thenReturn(2);
        when(rsMock.getInt("lecturer_id")).thenReturn(2);
        when(rsMock.getTimestamp("lesson_start")).thenReturn(Timestamp.valueOf(lessonStart));
        when(rsMock.getTimestamp("lesson_end")).thenReturn(Timestamp.valueOf(lessonEnd));
        when(rsMock.next()).thenReturn(true).thenReturn(false);

        assertEquals(expectedLesson, lessonMapper.mapRow(rsMock, 1));
    }
}
