package eu.leadconcult.schoolregistry.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses = new HashSet<>();
}
