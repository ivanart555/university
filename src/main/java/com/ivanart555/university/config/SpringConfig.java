package com.ivanart555.university.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Profile("prod")
@ComponentScan("com.ivanart555.university.dao")
@PropertySource("classpath:application.properties")
@PropertySource("classpath:sql.properties")

public class SpringConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringConfig.class);
    
    @Bean
    public DataSource dataSource() {
        Context context;
        DataSource dataSource = null;
        try {
            context = new InitialContext();
            Context initCtx  = (Context) context.lookup("java:/comp/env");
            dataSource = (DataSource) initCtx.lookup("jdbc/localPostgreSQL");
        } catch (NamingException e) {
            LOGGER.warn("Failed to look up JNDI resource!");
        }
        
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
