package com.ivanart555.university.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courses", schema = "university")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer id;

    @Column(name = "course_name", unique = true)
    @Pattern(regexp = "[a-z]+", message = "Course name must be lowercase")
    private String name;

    @Column(name = "course_description")
    @EqualsAndHashCode.Exclude
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Group> groups;

    @JsonIgnore
    @OneToOne(mappedBy = "course")
    @EqualsAndHashCode.Exclude
    private Lecturer lecturer;

    @JsonIgnore
    @OneToMany(mappedBy = "course")
    @EqualsAndHashCode.Exclude
    private Set<Lesson> lessons;

}
