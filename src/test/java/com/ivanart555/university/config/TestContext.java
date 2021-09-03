package com.ivanart555.university.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@ComponentScan("com.ivanart555.university")
public class TestContext {

}
