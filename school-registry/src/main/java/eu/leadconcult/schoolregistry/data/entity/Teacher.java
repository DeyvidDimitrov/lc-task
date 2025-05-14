package eu.leadconcult.schoolregistry.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    @ManyToMany
    @JoinTable(name = "teacher_social_groups",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses = new HashSet<>();
}
