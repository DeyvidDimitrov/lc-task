package eu.leadconcult.schoolregistry;

import eu.leadconcult.schoolregistry.controller.model.TeacherModel;
import eu.leadconcult.schoolregistry.data.entity.Teacher;
import eu.leadconcult.schoolregistry.data.repository.TeacherRepository;
import eu.leadconcult.schoolregistry.service.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void testCreateTeacher() {
        TeacherModel model = new TeacherModel("John", 35);
        Teacher expected = new Teacher();
        expected.setName("John");
        expected.setAge(35);

        when(teacherRepository.save(any(Teacher.class))).thenReturn(expected);

        Teacher result = teacherService.createTeacher(model);

        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals(35, result.getAge());

        verify(teacherRepository).save(any(Teacher.class));
    }

    @Test
    void testUpdateTeacher_Success() {
        UUID id = UUID.randomUUID();
        Teacher existing = new Teacher();
        existing.setId(id);
        existing.setName("John");
        existing.setAge(30);

        TeacherModel update = new TeacherModel("Jane", 40);

        when(teacherRepository.findById(id)).thenReturn(Optional.of(existing));
        when(teacherRepository.save(existing)).thenReturn(existing);

        Teacher result = teacherService.updateTeacher(id, update);

        assertEquals("Jane", result.getName());
        assertEquals(40, result.getAge());
        verify(teacherRepository).save(existing);
    }

    @Test
    void testUpdateTeacher_NotFound() {
        UUID id = UUID.randomUUID();
        when(teacherRepository.findById(id)).thenReturn(Optional.empty());

        TeacherModel model = new TeacherModel("Jane", 40);
        assertThrows(EntityNotFoundException.class, () -> teacherService.updateTeacher(id, model));
    }

    @Test
    void testGetById_Success() {
        UUID id = UUID.randomUUID();
        Teacher teacher = new Teacher();
        teacher.setId(id);

        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));

        Teacher result = teacherService.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void testGetById_NotFound() {
        UUID id = UUID.randomUUID();

        when(teacherRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.getById(id));
    }

    @Test
    void testDeleteTeacher_ByEntity() {
        Teacher teacher = new Teacher();
        teacher.setId(UUID.randomUUID());

        teacherService.deleteTeacher(teacher);

        verify(teacherRepository).delete(teacher);
    }
}
