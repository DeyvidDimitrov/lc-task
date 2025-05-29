package eu.leadconcult.schoolregistry;

import eu.leadconcult.schoolregistry.controller.converter.CollectionConversionService;
import eu.leadconcult.schoolregistry.controller.model.GroupModel;
import eu.leadconcult.schoolregistry.controller.view.CourseParticipantsView;
import eu.leadconcult.schoolregistry.controller.view.PersonView;
import eu.leadconcult.schoolregistry.data.entity.Group;
import eu.leadconcult.schoolregistry.data.entity.Student;
import eu.leadconcult.schoolregistry.data.repository.GroupRepository;
import eu.leadconcult.schoolregistry.service.GroupService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;


    @Mock
    private CollectionConversionService conversionService;

    @InjectMocks
    private GroupService groupService;

    @Test
    void testCreateGroup() {
        GroupModel model = new GroupModel("Group A");
        Group group = new Group();
        group.setName("Group A");

        when(groupRepository.save(any(Group.class))).thenReturn(group);

        Group result = groupService.createGroup(model);

        assertNotNull(result);
        assertEquals("Group A", result.getName());
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void testDeleteGroup_Success() {
        UUID id = UUID.randomUUID();

        groupService.deleteGroup(id);

        verify(groupRepository).deleteById(id);
    }

    @Test
    void testDeleteGroup_NullId_DoesNothing() {
        groupService.deleteGroup(null);

        verify(groupRepository, never()).deleteById(any());
    }

    @Test
    void testGetById_Found() {
        UUID id = UUID.randomUUID();
        Group group = new Group();
        group.setId(id);

        when(groupRepository.findById(id)).thenReturn(Optional.of(group));

        Group result = groupService.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void testGetById_NotFound() {
        UUID id = UUID.randomUUID();
        when(groupRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> groupService.getById(id));
    }

    @Test
    void testGetParticipantsInGroup() {
        UUID id = UUID.randomUUID();
        Group group = new Group();
        group.setId(id);

        Student student = new Student();
        group.setStudents(Set.of(student));

        when(groupRepository.findById(id)).thenReturn(Optional.of(group));
        when(conversionService.convert(anySet(), eq(PersonView.class)))
                .thenReturn(List.of(new PersonView()));

        CourseParticipantsView view = groupService.getParticipantsInGroup(id);

        assertNotNull(view);
        assertNotNull(view.getStudents());
        assertEquals(1, view.getStudents().size());
    }

}
