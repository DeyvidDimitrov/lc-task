package eu.leadconcult.schoolregistry;

import eu.leadconcult.schoolregistry.controller.model.CourseModel;
import eu.leadconcult.schoolregistry.data.entity.Course;
import eu.leadconcult.schoolregistry.data.entity.CourseType;
import eu.leadconcult.schoolregistry.data.entity.Student;
import eu.leadconcult.schoolregistry.data.entity.Teacher;
import eu.leadconcult.schoolregistry.data.repository.CourseRepository;
import eu.leadconcult.schoolregistry.exceptions.CourseWithoutTeacherException;
import eu.leadconcult.schoolregistry.service.CourseService;
import eu.leadconcult.schoolregistry.service.StudentService;
import eu.leadconcult.schoolregistry.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherService teacherService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private CourseService courseService;

    @Test
    void testCreateCourse_Success() {
        UUID teacherId = UUID.randomUUID();
        CourseModel model = new CourseModel("Math", CourseType.MAIN, teacherId, Collections.emptyList());

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);

        when(teacherService.getById(teacherId)).thenReturn(teacher);

        Course savedCourse = new Course();
        savedCourse.setName("Math");
        savedCourse.setType(CourseType.MAIN);
        savedCourse.setTeacher(teacher);

        when(courseRepository.save(any(Course.class))).thenReturn(savedCourse);

        Course result = courseService.createCourse(model);

        assertNotNull(result);
        assertEquals("Math", result.getName());
        assertEquals(CourseType.MAIN, result.getType());
        assertEquals(teacher, result.getTeacher());

        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testCreateCourse_TeacherNotFound_Throws() {
        UUID notExisting = UUID.randomUUID();
        CourseModel model = new CourseModel("Physics", CourseType.SECONDARY, notExisting, Collections.emptyList());

        when(teacherService.getById(notExisting)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> courseService.createCourse(model));
        verify(courseRepository, never()).save(any());
    }

    @Test
    void testCreateCourse_NoTeacher_ThrowsException() {
        CourseModel model = new CourseModel("Biology", CourseType.MAIN, null, Collections.emptyList());

        assertThrows(CourseWithoutTeacherException.class, () -> courseService.createCourse(model));
        verify(courseRepository, never()).save(any());
    }

    @Test
    void testCreateCourse_WithStudents_Success() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        CourseModel model = new CourseModel("Chemistry", CourseType.MAIN, teacherId, List.of(studentId));

        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        Student student = new Student();
        student.setId(studentId);

        when(teacherService.getById(teacherId)).thenReturn(teacher);
        when(studentService.getById(studentId)).thenReturn(student);
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArguments()[0]);

        Course result = courseService.createCourse(model);

        assertEquals(1, result.getStudents().size());
        assertTrue(result.getStudents().contains(student));
    }

    @Test
    void testDeleteCourse_Success() {
        UUID id = UUID.randomUUID();

        courseService.deleteCourse(id);

        verify(courseRepository).deleteById(id);
    }

    @Test
    void testDeleteCourse_NullId_DoesNothing() {
        courseService.deleteCourse(null);
        verify(courseRepository, never()).deleteById(any());
    }
}
