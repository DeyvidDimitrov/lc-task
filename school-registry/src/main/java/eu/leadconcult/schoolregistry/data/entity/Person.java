package eu.leadconcult.schoolregistry.data.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Person extends BaseEntity {
    private String name;
    private int age;
}
