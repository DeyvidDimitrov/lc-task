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
@Table(name = "student_groups")
public class Group extends BaseEntity {
    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<Student> students = new HashSet<>();
    
}