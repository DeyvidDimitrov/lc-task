package eu.leadconcult.schoolregistry.service;

import eu.leadconcult.schoolregistry.controller.converter.CollectionConversionService;
import eu.leadconcult.schoolregistry.controller.model.GroupModel;
import eu.leadconcult.schoolregistry.controller.view.CourseParticipantsView;
import eu.leadconcult.schoolregistry.controller.view.PersonView;
import eu.leadconcult.schoolregistry.data.entity.Group;
import eu.leadconcult.schoolregistry.data.entity.Student;
import eu.leadconcult.schoolregistry.data.repository.GroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final StudentService studentService;
    private final CollectionConversionService conversionService;

    @Transactional(readOnly = true)
    public Group getById(UUID courseId) {
        return groupRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException((String.format("Group with id %s not found", courseId))));
    }

    @Transactional
    public Group createGroup(GroupModel model) {
        Group group = new Group();
        group.setName(model.getName());
        return groupRepository.save(group);
    }

    @Transactional
    public void deleteGroup(UUID id) {
        if (id != null) {
            groupRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public List<Student> getStudentsInGroup(UUID id) {
        return studentService.getStudentsByGroupId(id);
    }

    @Transactional(readOnly = true)
    public CourseParticipantsView getParticipantsInGroup(UUID id) {
        Group group = getById(id);

        if (group == null) {
            return null;
        }

        CourseParticipantsView view = new CourseParticipantsView();
        view.setStudents(
                conversionService.convert(group.getStudents(), PersonView.class)
        );

        return view;
    }
}
