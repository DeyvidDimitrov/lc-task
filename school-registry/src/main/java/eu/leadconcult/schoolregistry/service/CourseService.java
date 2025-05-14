package eu.leadconcult.schoolregistry.service;

import eu.leadconcult.schoolregistry.controller.converter.CollectionConversionService;
import eu.leadconcult.schoolregistry.controller.model.CourseModel;
import eu.leadconcult.schoolregistry.controller.view.CourseParticipantsView;
import eu.leadconcult.schoolregistry.controller.view.PersonView;
import eu.leadconcult.schoolregistry.data.entity.Course;
import eu.leadconcult.schoolregistry.data.entity.Student;
import eu.leadconcult.schoolregistry.data.entity.Teacher;
import eu.leadconcult.schoolregistry.data.repository.CourseRepository;
import eu.leadconcult.schoolregistry.exceptions.CourseWithoutTeacherException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final CollectionConversionService conversionService;

    @Transactional(readOnly = true)
    public Course getById(UUID courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException((String.format("Course with id %s not found", courseId))));
    }

    @Transactional(readOnly = true)
    public long getCoursesCount() {
        return courseRepository.count();
    }

    @Transactional
    public Course createCourse(CourseModel courseModel) {
        Course course = new Course();
        course.setName(courseModel.getName());
        course.setType(courseModel.getType());

        if (courseModel.getTeacherId() != null) {
            Teacher teacher = teacherService.getById(courseModel.getTeacherId());
            course.setTeacher(teacher);
        } else {
            throw new CourseWithoutTeacherException("Course without teacher can't be created.");
        }

        if (!courseModel.getStudentIds().isEmpty()) {
            for (UUID studentId : courseModel.getStudentIds()) {
                try {
                    Student student = studentService.getById(studentId);
                    course.getStudents().add(student);
                } catch (EntityNotFoundException ignored) {
                }
            }
        }

        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(UUID id) {
        if (id != null) {
            courseRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public CourseParticipantsView getCourseParticipants(UUID id) {
        Course course = getById(id);

        if (course == null) {
            return null;
        }

        CourseParticipantsView view = new CourseParticipantsView();
        view.setStudents(
                conversionService.convert(course.getStudents(), PersonView.class)
        );
        view.setTeacher(
                conversionService.convert(course.getTeacher(), PersonView.class)
        );

        return view;
    }
}
