package com.ivanart555.university.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ivanart555.university.entities.Lesson;

@Component
public class LessonMapper implements RowMapper<Lesson> {

    @Override
    public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {

        Lesson lesson = new Lesson();
        lesson.setId(rs.getInt("lesson_id"));
        lesson.setCourseId(rs.getInt("course_id"));
        lesson.setRoomId(rs.getInt("room_id"));
        lesson.setLecturerId(rs.getInt("lecturer_id"));
        lesson.setLessonStart(rs.getTimestamp("lesson_start").toLocalDateTime());
        lesson.setLessonEnd(rs.getTimestamp("lesson_end").toLocalDateTime());

        return lesson;
    }
}
