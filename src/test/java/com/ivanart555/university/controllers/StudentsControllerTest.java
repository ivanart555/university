package com.ivanart555.university.controllers;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ivanart555.university.config.TestContext;
import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.config.TestSpringMvcConfig;
import com.ivanart555.university.services.StudentService;

@SpringJUnitWebConfig(classes = { TestSpringConfig.class, TestSpringMvcConfig.class, TestContext.class })
public class StudentsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private StudentService studentServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        Mockito.reset(studentServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void mockMvcTest() {
        assertNotNull(mockMvc);

    }

}
