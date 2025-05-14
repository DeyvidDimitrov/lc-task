package eu.leadconcult.schoolregistry.controller.view;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PersonView {
    private UUID id;
    private String name;
    private int age;
}
