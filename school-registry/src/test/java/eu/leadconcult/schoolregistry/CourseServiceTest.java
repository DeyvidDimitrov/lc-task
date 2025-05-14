package eu.leadconcult.schoolregistry;

import eu.leadconcult.schoolregistry.controller.model.CourseModel;
import eu.leadconcult.schoolregistry.data.entity.Course;
import eu.leadconcult.schoolregistry.data.entity.CourseType;
import eu.leadconcult.schoolregistry.data.entity.Teacher;
import eu.leadconcult.schoolregistry.data.repository.CourseRepository;
import eu.leadconcult.schoolregistry.data.repository.StudentRepository;
import eu.leadconcult.schoolregistry.data.repository.TeacherRepository;
import eu.leadconcult.schoolregistry.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse_Success() {
        UUID id1 = UUID.randomUUID();
        CourseModel model = new CourseModel("Math", CourseType.MAIN, id1, Collections.emptyList());

        Teacher teacher = new Teacher();
        teacher.setId(id1);

        when(teacherRepository.findById(id1)).thenReturn(Optional.of(teacher));

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
    void testCreateCourse_TeacherNotFound() {
        UUID notExisting = UUID.randomUUID();
        CourseModel model = new CourseModel("History", CourseType.MAIN, notExisting, Collections.emptyList());

        when(teacherRepository.findById(notExisting)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseService.createCourse(model));
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void testDeleteCourse_Success() {
        UUID id = UUID.randomUUID();
        Course course = new Course();
        course.setId(id);

        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        courseService.deleteCourse(id);

        verify(courseRepository).delete(course);
    }

    @Test
    void testDeleteCourse_NotFound() {
        UUID notExisting = UUID.randomUUID();
        when(courseRepository.findById(notExisting)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseService.deleteCourse(notExisting));
        verify(courseRepository, never()).delete(any());
    }
} 
