package eu.leadconcult.schoolregistry;

import eu.leadconcult.schoolregistry.controller.model.GroupModel;
import eu.leadconcult.schoolregistry.data.entity.Group;
import eu.leadconcult.schoolregistry.data.repository.GroupRepository;
import eu.leadconcult.schoolregistry.service.GroupService;
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

class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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
        Group group = new Group();
        UUID id = UUID.randomUUID();
        group.setId(id);

        when(groupRepository.findById(id)).thenReturn(Optional.of(group));

        groupService.deleteGroup(id);

        verify(groupRepository).delete(group);
    }

    @Test
    void testDeleteGroup_NotFound() {
        UUID notExisting = UUID.randomUUID();
        when(groupRepository.findById(notExisting)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> groupService.deleteGroup(notExisting));
    }
} 
