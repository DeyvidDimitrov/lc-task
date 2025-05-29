package eu.leadconcult.schoolregistry.service;

import eu.leadconcult.schoolregistry.controller.model.StudentFilter;
import eu.leadconcult.schoolregistry.controller.model.StudentModel;
import eu.leadconcult.schoolregistry.data.entity.Student;
import eu.leadconcult.schoolregistry.data.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public Student getById(UUID studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException((String.format("Student with id %s not found", studentId))));
    }

    @Transactional(readOnly = true)
    public long getStudentsCount() {
        return studentRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<Student> getStudentsFiltered(StudentFilter filter, Pageable pageable) {
        return studentRepository.findByCourses_IdAndAgeGreaterThanEqualOrderByNameAsc(filter.getCourseId(), filter.getMinAge(), pageable);
    }

    @Transactional(readOnly = true)
    public Page<Student> getStudentsByCourseId(UUID courseId, Pageable pageable) {
        return studentRepository.findByCourses_Id(courseId, pageable);
    }

    @Transactional
    public void deleteStudent(UUID id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found");
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public Student createStudent(StudentModel studentModel) {
        Student student = new Student();
        student.setAge(studentModel.getAge());
        student.setName(studentModel.getName());
        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(UUID studentId, StudentModel studentModel) {

        Student student = getById(studentId);
        if (studentModel.getName() != null) {
            student.setName(studentModel.getName());
        }

        if (studentModel.getAge() != null) {
            student.setAge(studentModel.getAge());
        }

        return studentRepository.save(student);
    }

    @Transactional
    public Page<Student> getStudentsByGroupId(UUID groupId, Pageable pageable) {
        return studentRepository.findByGroups_IdOrderByNameAsc(groupId, pageable);
    }
}
