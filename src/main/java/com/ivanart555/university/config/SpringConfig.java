package com.ivanart555.university.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.dao.impl.ClassroomDAOImpl;
import com.ivanart555.university.dao.impl.CourseDAOImpl;
import com.ivanart555.university.dao.impl.GroupDAOImpl;
import com.ivanart555.university.dao.impl.LecturerDAOImpl;
import com.ivanart555.university.dao.impl.LessonDAOImpl;
import com.ivanart555.university.dao.impl.StudentDAOImpl;
import com.ivanart555.university.services.impl.CourseServiceImpl;
import com.ivanart555.university.services.impl.GroupServiceImpl;
import com.ivanart555.university.services.impl.StudentServiceImpl;

@Configuration
@ComponentScan("com.ivanart555.university")
@PropertySource("classpath:application.properties")
@PropertySource("classpath:sql.properties")

public class SpringConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/university");
        dataSource.setUsername("postgres");
        dataSource.setPassword("1234");

        return dataSource;
    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:mem:school;DB_CLOSE_DELAY=-1");
//        dataSource.setUsername("h2");
//        dataSource.setPassword("1234");
//
//        return dataSource;
//    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public CourseDAOImpl courseDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        return new CourseDAOImpl(jdbcTemplate, env);
    }

    @Bean
    public GroupDAOImpl groupDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        return new GroupDAOImpl(jdbcTemplate, env);
    }

    @Bean
    public StudentDAOImpl studentDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        return new StudentDAOImpl(jdbcTemplate, env);
    }

    @Bean
    public LecturerDAOImpl lecturerDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        return new LecturerDAOImpl(jdbcTemplate, env);
    }

    @Bean
    public ClassroomDAOImpl classroomDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        return new ClassroomDAOImpl(jdbcTemplate, env);
    }

    @Bean
    public LessonDAOImpl lessonDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        return new LessonDAOImpl(jdbcTemplate, env);
    }

    @Bean
    public StudentServiceImpl studentServiceImpl(StudentDAO studentDAO, GroupDAO groupDAO, CourseDAO courseDAO, Environment env) {
        return new StudentServiceImpl(studentDAO, groupDAO, courseDAO, env);
    }

    @Bean
    public GroupServiceImpl groupServiceImpl(GroupDAO groupDAO) {
        return new GroupServiceImpl(groupDAO);
    }

    @Bean
    public CourseServiceImpl courseServiceImpl(CourseDAO courseDAO) {
        return new CourseServiceImpl(courseDAO);
    }
}
