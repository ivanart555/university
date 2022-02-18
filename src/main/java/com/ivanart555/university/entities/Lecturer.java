package com.ivanart555.university.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lecturers", schema = "university")
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecturer_id")
    private Integer id;

    @Column(name = "lecturer_name")
    @Pattern(regexp = "[A-Z][a-z]+", message = "First name must be capitalized")
    private String firstName;

    @Column(name = "lecturer_lastname")
    @Pattern(regexp = "[A-Z][a-z]+", message = "Last name must be capitalized")
    private String lastName;

    @Column(name = "active")
    private boolean active;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @EqualsAndHashCode.Exclude
    private Course course;

}
