package com.ivanart555.university.entities;

import java.time.LocalDateTime;

public class LessonDuration {
    private LocalDateTime lessonStart;
    private LocalDateTime lessonEnd;

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
        return "LessonDuration [lessonStart=" + lessonStart + ", lessonEnd=" + lessonEnd + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        LessonDuration other = (LessonDuration) obj;
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
