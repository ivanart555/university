package com.ivanart555.university.main;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.ivanart555.university.config.SpringConfig;
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.dao.impl.CourseDAOImpl;
import com.ivanart555.university.dao.impl.GroupDAOImpl;
import com.ivanart555.university.dao.impl.StudentDAOImpl;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.DAOException;

public class Main {

    public static void main(String[] args) throws DAOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringConfig.class);

        // TestDAO
        CourseDAO courseDAO = context.getBean("courseDAOImpl", CourseDAOImpl.class);

        List<Course> coursesList = courseDAO.getAll();

        for (Course course : coursesList) {
            System.out.println(course.toString());
        }

        StudentDAO studentDAO = context.getBean("studentDAOImpl", StudentDAOImpl.class);

        List<Student> studentsList = studentDAO.getAll();

        for (Student student : studentsList) {
            System.out.println(student.toString());
        }

        GroupDAO groupDAO = context.getBean("groupDAOImpl", GroupDAOImpl.class);

        List<Group> groupsList = groupDAO.getAll();

        for (Group groups : groupsList) {
            System.out.println(groups.toString());
        }

//        Course newCourse = new Course("biology", "");

//        courseDAO.create(newCourse);

        context.close();
    }

}
