package eu.leadconcult.schoolregistry.controller;

import eu.leadconcult.schoolregistry.controller.model.StudentFilter;
import eu.leadconcult.schoolregistry.controller.model.StudentModel;
import eu.leadconcult.schoolregistry.data.entity.Student;
import eu.leadconcult.schoolregistry.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public void createStudent(@Valid @RequestBody StudentModel studentModel) {
        studentService.createStudent(studentModel);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable UUID id, @Valid @RequestBody StudentModel studentModel) {
        return studentService.updateStudent(id, studentModel);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);
    }

    @GetMapping
    public List<Student> getStudentsFiltered(StudentFilter filter, Pageable pageable) {
        return studentService.getStudentsFiltered(filter, pageable);
    }

    @GetMapping("/count")
    public long getStudentCount() {
        return studentService.getStudentsCount();
    }
}
