package com.ivanart555.university.services.impl;

import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.CourseRepository;
import com.ivanart555.university.services.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public List<Course> findAll() throws ServiceException {
        List<Course> courses = courseRepository.findAll();
        log.info("All Courses received successfully.");
        return courses;
    }

    @Override
    public Page<Course> findAll(Pageable pageable) throws ServiceException {
        Page<Course> courses = courseRepository.findAll(pageable);
        log.info("All Courses received successfully.");
        return courses;
    }

    @Override
    public Course findById(Integer id) throws ServiceException {
        Course course = null;
        try {
            course = courseRepository.findById(id)
                    .orElseThrow(() -> new ServiceException(String.format("Course with id %d not found!", id)));
        } catch (ServiceException e) {
            log.warn("Course with id {} not found!", id);
        }
        log.info("Course with id {} received successfully.", id);

        return course;
    }

    @Override
    public void deleteById(Integer id) throws ServiceException {
        courseRepository.deleteById(id);
        log.info("Course with id {} deleted successfully.", id);
    }

    @Override
    public int save(Course course) throws ServiceException {
        Course createdCourse = courseRepository.save(course);
        log.info("Course with id {} saved successfully.", createdCourse.getId());
        return createdCourse.getId();
    }
}
