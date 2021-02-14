package com.ivanart555.university.entities;

import java.util.List;

public class University {
    private List<Lecturer> lecturers;
    private List<Student> students;
    private List<Course> courses;
    private List<Group> groups;
    private Timetable timetable;
    private List<ClassRoom> classRooms;

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public List<ClassRoom> getClassRooms() {
        return classRooms;
    }

    public void setClassRooms(List<ClassRoom> classRooms) {
        this.classRooms = classRooms;
    }

    @Override
    public String toString() {
        return "University [lecturers=" + lecturers + ", students=" + students + ", courses=" + courses + ", groups="
                + groups + ", timetable=" + timetable + ", classRooms=" + classRooms + "]";
    }
}
