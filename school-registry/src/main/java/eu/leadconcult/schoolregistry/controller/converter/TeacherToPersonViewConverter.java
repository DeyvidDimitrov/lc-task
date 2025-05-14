package eu.leadconcult.schoolregistry.controller.converter;

import eu.leadconcult.schoolregistry.controller.view.PersonView;
import eu.leadconcult.schoolregistry.data.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherToPersonViewConverter extends BaseConverter<Teacher, PersonView> {

    @Override
    public PersonView convert(Teacher source) {
        var view = new PersonView();
        view.setId(source.getId());
        view.setName(source.getName());
        view.setAge(source.getAge());
        return view;
    }

}
