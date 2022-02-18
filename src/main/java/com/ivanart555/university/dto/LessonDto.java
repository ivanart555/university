package com.ivanart555.university.dto;

import com.ivanart555.university.annotations.LessonDatesTimes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@LessonDatesTimes(lessonStart = "lessonStart", lessonEnd = "lessonEnd")
public class LessonDto {

    private int id;
    @NotNull
    private Integer courseId;
    private String courseName;
    @NotNull
    private Integer roomId;
    private String roomName;
    @NotNull
    private Integer lecturerId;
    private String lecturerFirstName;
    private String lecturerLastName;
    @NotNull
    private Integer groupId;
    private String groupName;
    private LocalDateTime lessonStart;
    private LocalDateTime lessonEnd;

}
