package eu.leadconcult.schoolregistry.data.entity;

import jakarta.persistence.Column;
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
@Table(name = "social_groups")
public class Group extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<Student> students = new HashSet<>();

    @ManyToMany(mappedBy = "groups")
    private Set<Teacher> teachers = new HashSet<>();
}