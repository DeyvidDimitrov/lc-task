package eu.leadconcult.schoolregistry.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseType type = CourseType.MAIN;

    @ManyToOne(optional = false)
    private Teacher teacher;

    @ManyToMany
    private Set<Student> students = new HashSet<>();
}
