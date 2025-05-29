package eu.leadconcult.schoolregistry.service;

import eu.leadconcult.schoolregistry.controller.model.TeacherModel;
import eu.leadconcult.schoolregistry.data.entity.Course;
import eu.leadconcult.schoolregistry.data.entity.Teacher;
import eu.leadconcult.schoolregistry.data.repository.TeacherRepository;
import eu.leadconcult.schoolregistry.exceptions.CourseWithoutTeacherException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public long getTeacherCount() {
        return teacherRepository.count();
    }

    @Transactional(readOnly = true)
    public Teacher getById(UUID teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Teacher with id %s not found", teacherId)));
    }

    @Transactional
    public Teacher createTeacher(TeacherModel teacherModel) {
        Teacher teacher = new Teacher();
        teacher.setAge(teacherModel.getAge());
        teacher.setName(teacherModel.getName());
        return teacherRepository.save(teacher);
    }

    @Transactional
    public Teacher updateTeacher(UUID id, TeacherModel teacherModel) {
        Teacher teacher = getById(id);
        if (teacherModel.getName() != null) {
            teacher.setName(teacherModel.getName());
        }

        if (teacherModel.getAge() != null) {
            teacher.setAge(teacherModel.getAge());
        }

        return teacherRepository.save(teacher);
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }
}
