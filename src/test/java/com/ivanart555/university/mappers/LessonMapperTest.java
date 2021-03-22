package com.ivanart555.university.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Lesson;

@SpringJUnitConfig(TestSpringConfig.class)
class LessonMapperTest {

    @Autowired
    private LessonMapper lessonMapper;

    @Test
    void shouldReturnGroup_WhenCalledMapRow() throws SQLException {
        LocalDateTime lessonStart = LocalDateTime.now();
        LocalDateTime lessonEnd = LocalDateTime.now().plusHours(1);

        Lesson expectedLesson = new Lesson(1, 3, 2, lessonStart, lessonEnd);

        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getInt("lesson_id")).thenReturn(1);
        Mockito.when(rsMock.getInt("course_id")).thenReturn(3);
        Mockito.when(rsMock.getInt("room_id")).thenReturn(2);
        Mockito.when(rsMock.getTimestamp("lesson_start")).thenReturn(Timestamp.valueOf(lessonStart));
        Mockito.when(rsMock.getTimestamp("lesson_end")).thenReturn(Timestamp.valueOf(lessonEnd));
        Mockito.when(rsMock.next()).thenReturn(true).thenReturn(false);

        assertEquals(expectedLesson, lessonMapper.mapRow(rsMock, 1));
    }
}
