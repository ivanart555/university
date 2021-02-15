package com.ivanart555.university.entities;

import java.util.List;

public class Timetable {
    private List<Lesson> lessons;

    public List<Lesson> getTimetable() {
        return lessons;
    }

    public void setTimetable(List<Lesson> timetable) {
        this.lessons = timetable;
    }

    @Override
    public String toString() {
        return "Timetable [lessons=" + lessons + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lessons == null) ? 0 : lessons.hashCode());
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
        Timetable other = (Timetable) obj;
        if (lessons == null) {
            if (other.lessons != null)
                return false;
        } else if (!lessons.equals(other.lessons))
            return false;
        return true;
    }
}
