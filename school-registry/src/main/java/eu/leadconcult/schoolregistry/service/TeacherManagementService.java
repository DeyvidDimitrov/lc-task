package eu.leadconcult.schoolregistry.service;

import eu.leadconcult.schoolregistry.data.entity.Course;
import eu.leadconcult.schoolregistry.exceptions.CourseWithoutTeacherException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherManagementService {
    private final TeacherService teacherService;
    private final CourseService courseService;

    @Transactional
    public void deleteTeacher(UUID id, boolean force) {
        var teacher = teacherService.getById(id);
        var courses = courseService.getCoursesTaughtByTeacher(id);

        if (!courses.isEmpty() && !force) {
            throw new CourseWithoutTeacherException("Cannot delete teacher without confirmation.");
        }

        for (Course course : courses) {
            courseService.unassignedTeacher(course);
        }

        teacherService.deleteTeacher(teacher);
    }
}
