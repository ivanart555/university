package com.ivanart555.university.entities;

import java.time.LocalDateTime;

public class Lesson {
    private int id;
    private int courseId;
    private int roomId;
    private LocalDateTime lessonStart;
    private LocalDateTime lessonEnd;

    public Lesson() {}

    public Lesson(int id, int courseId, int roomId, LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        this.id = id;
        this.courseId = courseId;
        this.roomId = roomId;
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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
        return "Lesson [lessonId=" + id + ", courseId=" + courseId + ", roomId=" + roomId + ", lessonStart="
                + lessonStart + ", lessonEnd=" + lessonEnd + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + courseId;
        result = prime * result + ((lessonEnd == null) ? 0 : lessonEnd.hashCode());
        result = prime * result + id;
        result = prime * result + ((lessonStart == null) ? 0 : lessonStart.hashCode());
        result = prime * result + roomId;
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
        Lesson other = (Lesson) obj;
        if (courseId != other.courseId)
            return false;
        if (lessonEnd == null) {
            if (other.lessonEnd != null)
                return false;
        } else if (!lessonEnd.equals(other.lessonEnd))
            return false;
        if (id != other.id)
            return false;
        if (lessonStart == null) {
            if (other.lessonStart != null)
                return false;
        } else if (!lessonStart.equals(other.lessonStart))
            return false;
        if (roomId != other.roomId)
            return false;
        return true;
    }
}
