package com.ivanart555.university.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.ivanart555.university.services.StudentService;

@Configuration
@Profile("test")
@ComponentScan("com.ivanart555.university.services.impl")
@PropertySource("classpath:application.properties")
@PropertySource("classpath:sql.properties")
public class TestContext {

    @Bean
    public StudentService studentService() {
        return Mockito.mock(StudentService.class);
    }

}
