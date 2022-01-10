package com.ivanart555.university.entities.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.CourseService;

@Component
public class StringCourseConverter implements Converter<String, Course> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringCourseConverter.class);
    private CourseService courseService;

    @Autowired
    public StringCourseConverter(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public Course convert(String id) {
        Course course = new Course();

        try {
            course = courseService.findById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            LOGGER.error("Failed to convert id of the Course to Integer!");
        } catch (ServiceException e) {
            LOGGER.error("Failed to get Course by id!");
        }
        return course;
    }

}
