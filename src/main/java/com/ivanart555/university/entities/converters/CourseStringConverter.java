package com.ivanart555.university.entities.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ivanart555.university.entities.Course;

@Component
public class CourseStringConverter implements Converter<Course, String> {

    @Override
    public String convert(Course course) {
        return course.getId().toString();
    }

}
