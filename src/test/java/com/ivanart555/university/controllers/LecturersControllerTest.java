package com.ivanart555.university.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.services.LecturerService;

@SpringJUnitWebConfig(TestContext.class)
@ExtendWith(MockitoExtension.class)
class LecturersControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LecturerService lecturerService;

    @Mock
    Page<Lecturer> anyPage;

    @InjectMocks
    private LecturersController lecturersController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(lecturersController).build();
    }

    @Test
    void mockMvcTest() {
        assertNotNull(mockMvc);
    }

    @Test
    void studentsControllerTest() throws Exception {
        when(lecturerService.findPaginated(any())).thenReturn(anyPage);
        mockMvc.perform(get("/lecturers"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lecturerPage"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(view().name("lecturers/index"));
    }
}
