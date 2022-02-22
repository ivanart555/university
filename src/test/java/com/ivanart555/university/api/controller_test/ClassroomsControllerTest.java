package com.ivanart555.university.api.controller_test;

import com.ivanart555.university.api.controller.ClassroomsController;
import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.services.ClassroomService;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class ClassroomsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClassroomService classroomService;

    @Mock
    Page<Classroom> anyPage;

    @InjectMocks
    private ClassroomsController classroomsController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(classroomsController).build();
    }

    @Test
    void mockMvcTest() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnViewClassroomsIndex_whenCalledClassroomsGET() throws Exception {
        when(classroomService.findAll(any())).thenReturn(anyPage);
        mockMvc.perform(get("/classrooms"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("classroomPage"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("classroom"))
                .andExpect(view().name("classrooms/index"));
    }

    @Test
    void shouldRedirectToClassrooms_whenCalledClassroomsPOST() throws Exception {
        mockMvc.perform(post("/classrooms"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("classroom"))
                .andExpect(view().name("redirect:/classrooms"));
    }

    @Test
    void shouldRedirectToClassrooms_whenCalledClassroomsEditIdPATCH() throws Exception {
        mockMvc.perform(patch("/classrooms/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("classroom"))
                .andExpect(view().name("redirect:/classrooms"));
    }

    @Test
    void shouldRedirectToClassrooms_whenCalledClassroomsDeleteIdDELETE() throws Exception {
        mockMvc.perform(delete("/classrooms/delete/20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/classrooms"));
    }
}
