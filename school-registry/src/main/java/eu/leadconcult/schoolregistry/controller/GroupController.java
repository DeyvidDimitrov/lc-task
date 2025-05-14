package eu.leadconcult.schoolregistry.controller;

import eu.leadconcult.schoolregistry.controller.converter.CollectionConversionService;
import eu.leadconcult.schoolregistry.controller.view.CourseParticipantsView;
import eu.leadconcult.schoolregistry.controller.view.PersonView;
import eu.leadconcult.schoolregistry.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final CollectionConversionService conversionService;

    @GetMapping("/{id}/students")
    public Page<PersonView> getStudentsInGroup(@PathVariable UUID id, Pageable pageable) {
        return conversionService.convert(groupService.getStudentsInGroup(id, pageable), PersonView.class);
    }

    @GetMapping("/{id}")
    public CourseParticipantsView getParticipantsInGroup(@PathVariable UUID id) {
        return groupService.getParticipantsInGroup(id);
    }

}
