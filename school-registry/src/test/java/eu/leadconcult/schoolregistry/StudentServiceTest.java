package eu.leadconcult.schoolregistry;

import eu.leadconcult.schoolregistry.controller.model.StudentModel;
import eu.leadconcult.schoolregistry.data.entity.Student;
import eu.leadconcult.schoolregistry.data.repository.StudentRepository;
import eu.leadconcult.schoolregistry.service.StudentService;
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
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void testCreateStudent() {
        StudentModel model = new StudentModel("Alice", 21);
        Student student = new Student();
        student.setName("Alice");
        student.setAge(21);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.createStudent(model);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals(21, result.getAge());

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void testDeleteStudent_Success() {
        UUID id = UUID.randomUUID();

        when(studentRepository.existsById(id)).thenReturn(true);

        studentService.deleteStudent(id);

        verify(studentRepository).deleteById(id);
    }

    @Test
    void testDeleteStudent_NotFound() {
        UUID notExisting = UUID.randomUUID();

        when(studentRepository.existsById(notExisting)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> studentService.deleteStudent(notExisting));
        verify(studentRepository, never()).deleteById(any());
    }

} 
