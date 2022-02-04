package com.ivanart555.university.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lessons", schema = "university")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_room_id")
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_lecturer_id")
    private Lecturer lecturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_group_id")
    private Group group;

    @Column(name = "lesson_start")
    private LocalDateTime lessonStart;

    @Column(name = "lesson_end")
    private LocalDateTime lessonEnd;

    public Lesson() {
    }

    public Lesson(LocalDateTime lessonStart,
                  LocalDateTime lessonEnd) {
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonEnd;
    }

    public Lesson(Course course, Classroom classroom, Lecturer lecturer, Group group, LocalDateTime lessonStart,
                  LocalDateTime lessonEnd) {
        this.course = course;
        this.classroom = classroom;
        this.lecturer = lecturer;
        this.group = group;
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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
        return "Lesson [id=" + id + ", course=" + course + ", classroom=" + classroom + ", lecturer=" + lecturer
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
        Lesson other = (Lesson) obj;
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
