package com.ivanart555.university.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CourseMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {

        Course course = new Course();
        course.setCourseId(rs.getInt("course_id"));
        course.setCourseName(rs.getString("room_name"));
        course.setCourseDescription(rs.getString("room_name"));

        return course;
    }
}
