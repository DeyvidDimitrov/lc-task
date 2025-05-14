package eu.leadconcult.schoolregistry;

import eu.leadconcult.schoolregistry.controller.model.TeacherModel;
import eu.leadconcult.schoolregistry.data.entity.Teacher;
import eu.leadconcult.schoolregistry.data.repository.TeacherRepository;
import eu.leadconcult.schoolregistry.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTeacher() {
        TeacherModel model = new TeacherModel("John", 35);
        Teacher teacher = new Teacher();
        teacher.setName("John");
        teacher.setAge(35);

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        Teacher result = teacherService.createTeacher(model);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(35, result.getAge());

        verify(teacherRepository).save(any(Teacher.class));
    }

    @Test
    void testUpdateTeacher_Success() {
        TeacherModel model = new TeacherModel("Jane", 40);
        Teacher existing = new Teacher();
        existing.setName("John");
        existing.setAge(35);
        UUID id = UUID.randomUUID();
        existing.setId(id);

        when(teacherRepository.findById(id)).thenReturn(Optional.of(existing));
        when(teacherRepository.save(existing)).thenReturn(existing);

        Teacher updated = teacherService.updateTeacher(id, model);

        assertEquals("Jane", updated.getName());
        assertEquals(40, updated.getAge());

        verify(teacherRepository).save(existing);
    }

    @Test
    void testUpdateTeacher_NotFound() {
        TeacherModel model = new TeacherModel("Test", 25);
        UUID notExisting = UUID.randomUUID();

        when(teacherRepository.findById(notExisting)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.updateTeacher(notExisting, model));
    }

    @Test
    void testDeleteTeacher_Success() {
        Teacher teacher = new Teacher();
        UUID id = UUID.randomUUID();
        teacher.setId(id);

        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));

        teacherService.deleteTeacher(id);

        verify(teacherRepository).delete(teacher);
    }

    @Test
    void testDeleteTeacher_NotFound() {
        UUID notExisting = UUID.randomUUID();
        when(teacherRepository.findById(notExisting)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.deleteTeacher(notExisting));
    }
} 
