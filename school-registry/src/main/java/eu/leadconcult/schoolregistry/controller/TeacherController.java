package eu.leadconcult.schoolregistry.controller;

import eu.leadconcult.schoolregistry.controller.converter.CollectionConversionService;
import eu.leadconcult.schoolregistry.controller.model.TeacherModel;
import eu.leadconcult.schoolregistry.controller.view.PersonView;
import eu.leadconcult.schoolregistry.data.entity.Teacher;
import eu.leadconcult.schoolregistry.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final CollectionConversionService conversionService;

    @GetMapping("/count")
    public long getTeacherCount() {
        return teacherService.getTeacherCount();
    }

    @PostMapping
    public void createTeacher(@Valid @RequestBody TeacherModel teacherModel) {
        teacherService.createTeacher(teacherModel);
    }

    @PutMapping("/{id}")
    public PersonView updateTeacher(@PathVariable UUID id, @Valid @RequestBody TeacherModel teacherModel) {
        return conversionService.convert(teacherService.updateTeacher(id, teacherModel), PersonView.class);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable UUID id) {
        teacherService.deleteTeacher(id);
    }
}
