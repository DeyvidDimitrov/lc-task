package eu.leadconcult.schoolregistry.controller.converter;

import eu.leadconcult.schoolregistry.controller.view.PersonView;
import eu.leadconcult.schoolregistry.data.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentToPersonViewConverter extends BaseConverter<Student, PersonView> {

    @Override
    public PersonView convert(Student source) {
        var view = new PersonView();
        view.setId(source.getId());
        view.setName(source.getName());
        view.setAge(source.getAge());
        return view;
    }

}
