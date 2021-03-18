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

@Configuration
@ComponentScan("com.ivanart555.university")
@PropertySource("classpath:application.properties")
@PropertySource("classpath:sql.properties")

public class TestSpringConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:school;DB_CLOSE_DELAY=-1");
//        dataSource.setUrl("jdbc:h2:tcp://localhost/~/test2");
        dataSource.setUsername("h2");
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
}
