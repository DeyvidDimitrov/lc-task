package eu.leadconcult.schoolregistry.controller;

import eu.leadconcult.schoolregistry.controller.converter.CollectionConversionService;
import eu.leadconcult.schoolregistry.controller.model.StudentFilter;
import eu.leadconcult.schoolregistry.controller.model.StudentModel;
import eu.leadconcult.schoolregistry.controller.view.PersonView;
import eu.leadconcult.schoolregistry.data.entity.Student;
import eu.leadconcult.schoolregistry.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final CollectionConversionService conversionService;

    @PostMapping
    public void createStudent(@Valid @RequestBody StudentModel studentModel) {
        studentService.createStudent(studentModel);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable UUID id, @Valid @RequestBody StudentModel studentModel) {
        studentService.updateStudent(id, studentModel);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);
    }

    @GetMapping
    public Page<PersonView> getStudentsFiltered(StudentFilter filter, Pageable pageable) {
        return conversionService.convert(studentService.getStudentsFiltered(filter, pageable), PersonView.class);
    }

    @GetMapping("/count")
    public long getStudentCount() {
        return studentService.getStudentsCount();
    }
}
