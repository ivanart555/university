package com.ivanart555.university.controllers;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.ivanart555.university.config.TestContext;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.services.StudentService;

@SpringJUnitWebConfig(TestContext.class)
@ExtendWith(MockitoExtension.class)
class StudentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    private StudentsController studentsController;

    @BeforeEach
    public void setup() {
        Mockito.reset(studentService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void mockMvcTest() {
        assertNotNull(mockMvc);

    }

    @Test
    void studentsControllerTest() throws Exception {
        List<Student> studentsToReturn = new ArrayList<>();
        studentsToReturn.add(new Student(1, "Ivan", "Smith", 3));
        studentsToReturn.add(new Student(2, "Alex", "Smith", 2));
        when(studentService.getAll()).thenReturn(studentsToReturn);

        mockMvc.perform(get("/students")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Ivan")));

    }

}
