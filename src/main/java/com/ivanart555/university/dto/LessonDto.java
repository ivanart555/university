package com.ivanart555.university.dto;

import com.ivanart555.university.annotations.LessonDatesTimes;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    public LessonDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Integer lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerFirstName() {
        return lecturerFirstName;
    }

    public void setLecturerFirstName(String lecturerFirstName) {
        this.lecturerFirstName = lecturerFirstName;
    }

    public String getLecturerLastName() {
        return lecturerLastName;
    }

    public void setLecturerLastName(String lecturerLastName) {
        this.lecturerLastName = lecturerLastName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDateTime getLessonStart() {
        return lessonStart;
    }

    public void setLessonStart(LocalDateTime lessonStart) {
        this.lessonStart = lessonStart;
    }

    public LocalDateTime getLessonEnd() {
        return lessonEnd;
    }

    public void setLessonEnd(LocalDateTime lessonEnd) {
        this.lessonEnd = lessonEnd;
    }

    @Override
    public String toString() {
        return "LessonDto [id=" + id + ", courseId=" + courseId + ", courseName=" + courseName + ", roomId=" + roomId
                + ", roomName=" + roomName + ", lecturerId=" + lecturerId + ", lecturerFirstName=" + lecturerFirstName
                + ", lecturerLastName=" + lecturerLastName + ", groupId=" + groupId + ", groupName=" + groupName
                + ", lessonStart=" + lessonStart + ", lessonEnd=" + lessonEnd + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((lessonEnd == null) ? 0 : lessonEnd.hashCode());
        result = prime * result + ((lessonStart == null) ? 0 : lessonStart.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LessonDto other = (LessonDto) obj;
        if (id != other.id)
            return false;
        if (lessonEnd == null) {
            if (other.lessonEnd != null)
                return false;
        } else if (!lessonEnd.equals(other.lessonEnd))
            return false;
        if (lessonStart == null) {
            if (other.lessonStart != null)
                return false;
        } else if (!lessonStart.equals(other.lessonStart))
            return false;
        return true;
    }

}
