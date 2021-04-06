package com.ivanart555.university.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.ivanart555.university.dao.impl.ClassroomDAOImpl;
import com.ivanart555.university.dao.impl.CourseDAOImpl;
import com.ivanart555.university.dao.impl.GroupDAOImpl;
import com.ivanart555.university.dao.impl.LecturerDAOImpl;
import com.ivanart555.university.dao.impl.LessonDAOImpl;
import com.ivanart555.university.dao.impl.StudentDAOImpl;
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

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public CourseDAOImpl courseDAOImpl(JdbcTemplate jdbcTemplate) {
        return new CourseDAOImpl(jdbcTemplate);
    }

    @Bean
    public GroupDAOImpl groupDAOImpl(JdbcTemplate jdbcTemplate) {
        return new GroupDAOImpl(jdbcTemplate);
    }

    @Bean
    public StudentDAOImpl studentDAOImpl(JdbcTemplate jdbcTemplate) {
        return new StudentDAOImpl(jdbcTemplate);
    }

    @Bean
    public LecturerDAOImpl lecturerDAOImpl(JdbcTemplate jdbcTemplate) {
        return new LecturerDAOImpl(jdbcTemplate);
    }

    @Bean
    public ClassroomDAOImpl classroomDAOImpl(JdbcTemplate jdbcTemplate) {
        return new ClassroomDAOImpl(jdbcTemplate);
    }

    @Bean
    public LessonDAOImpl lessonDAOImpl(JdbcTemplate jdbcTemplate) {
        return new LessonDAOImpl(jdbcTemplate);
    }

    @Bean
    public StudentServiceImpl studentServiceImpl() {
        return new StudentServiceImpl();
    }

    @Bean
    public GroupServiceImpl groupServiceImpl() {
        return new GroupServiceImpl();
    }
}
