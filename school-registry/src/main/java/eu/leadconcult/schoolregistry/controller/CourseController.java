package eu.leadconcult.schoolregistry.controller;

import eu.leadconcult.schoolregistry.controller.model.CourseModel;
import eu.leadconcult.schoolregistry.controller.view.CourseParticipantsView;
import eu.leadconcult.schoolregistry.data.entity.Student;
import eu.leadconcult.schoolregistry.service.CourseService;
import eu.leadconcult.schoolregistry.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final StudentService studentService;

    @PostMapping
    public void createStudent(@RequestBody CourseModel courseModel) {
        courseService.createCourse(courseModel);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable UUID id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/count")
    public long getCoursesCount() {
        return courseService.getCoursesCount();
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsInCourse(@PathVariable UUID id) {
        return studentService.getStudentsByCourseId(id);
    }

    @GetMapping("/{id}/participants")
    public CourseParticipantsView getParticipantsInCourse(@PathVariable UUID id) {
        return courseService.getCourseParticipants(id);
    }
}
