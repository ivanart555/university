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
@Table(name = "students", schema = "university")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int id;

    @Column(name = "student_name")
    @Pattern(regexp = "[A-Z][a-z]+", message = "First name must be capitalized")
    private String firstName;

    @Column(name = "student_lastname")
    @Pattern(regexp = "[A-Z][a-z]+", message = "Last name must be capitalized")
    private String lastName;

    @Column(name = "active")
    private boolean active;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Group group;

}
