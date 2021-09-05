package com.ivanart555.university.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ivanart555.university.config.TestContext;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.services.StudentService;

@SpringJUnitWebConfig(TestContext.class)
@ExtendWith(MockitoExtension.class)
class StudentsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @Mock
    Page<Student> anyPage;

    @InjectMocks
    private StudentsController studentsController;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(studentsController).build();
    }

    @Test
    void mockMvcTest() {
        assertNotNull(mockMvc);

    }

    @Test
    void studentsControllerTest() throws Exception {

        when(studentService.findPaginated(any())).thenReturn(anyPage);

        mockMvc.perform(get("/students")).andDo(print())
                .andExpect(view().name("students/index"));

    }

}
