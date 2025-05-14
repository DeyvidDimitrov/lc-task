package eu.leadconcult.schoolregistry.controller;

import eu.leadconcult.schoolregistry.controller.model.TeacherModel;
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

    @GetMapping("/count")
    public long getTeacherCount() {
        return teacherService.getTeacherCount();
    }

    @PostMapping
    public void createTeacher(@Valid @RequestBody TeacherModel teacherModel) {
        teacherService.createTeacher(teacherModel);
    }

    @PutMapping("/{id}")
    public Teacher updateTeacher(@PathVariable UUID id, @Valid @RequestBody TeacherModel teacherModel) {
        return teacherService.updateTeacher(id, teacherModel);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable UUID id) {
        teacherService.deleteTeacher(id);
    }
}
