package com.ivanart555.university.api.controller_test;

import com.ivanart555.university.api.controller.LessonsController;
import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.dto.LessonDto;
import com.ivanart555.university.services.*;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class LessonsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LessonService lessonService;
    @Mock
    private CourseService courseService;
    @Mock
    private LecturerService lecturerService;
    @Mock
    private ClassroomService classroomService;
    @Mock
    private GroupService groupService;

    @Mock
    Page<LessonDto> anyPage;

    @InjectMocks
    private LessonsController lessonsController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(lessonsController).build();
    }

    @Test
    void mockMvcTest() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnViewLessonsIndex_whenCalledLessonsGET() throws Exception {
        when(lessonService.findAll(any())).thenReturn(anyPage);
        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lessonPage"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("lessonDto"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("lecturers"))
                .andExpect(model().attributeExists("classrooms"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(view().name("lessons/index"));
    }

    @Test
    void shouldRedirectToLessons_whenCalledLessonsPOST() throws Exception {
        LessonDto lessonDto = new LessonDto(1, 1, "math", 1, "100", 1, "Alex", "Black", 1, "FW-54", LocalDateTime.now(), LocalDateTime.now().plusMinutes(60));
        mockMvc.perform(post("/lessons").flashAttr("lessonDto", lessonDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("lessonDto"))
                .andExpect(view().name("redirect:/lessons"));
    }

    @Test
    void shouldRedirectToLessons_whenCalledLessonsEditIdPATCH() throws Exception {
        LessonDto lessonDto = new LessonDto(1, 1, "math", 1, "100", 1, "Alex", "Black", 1, "FW-54", LocalDateTime.now(), LocalDateTime.now().plusMinutes(60));
        mockMvc.perform(patch("/lessons/edit").flashAttr("lessonDto", lessonDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("lessonDto"))
                .andExpect(view().name("redirect:/lessons"));
    }

    @Test
    void shouldRedirectToLessons_whenCalledLessonsDeleteIdDELETE() throws Exception {
        mockMvc.perform(delete("/lessons/delete/20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/lessons"));
    }
}
