package eu.leadconcult.schoolregistry.controller.view;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CourseParticipantsView {
    PersonView teacher;
    List<PersonView> students = new ArrayList<>();
}
