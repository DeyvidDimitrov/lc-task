package eu.leadconcult.schoolregistry.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student extends Person {

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    @ManyToMany
    private Set<Group> groups = new HashSet<>();
}
