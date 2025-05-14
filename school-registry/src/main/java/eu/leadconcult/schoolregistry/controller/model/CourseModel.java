package eu.leadconcult.schoolregistry.controller.model;

import eu.leadconcult.schoolregistry.data.entity.CourseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseModel {

    @NotBlank
    private String name;
    @NotNull
    private CourseType type = CourseType.MAIN;
    private UUID teacherId;
    private List<UUID> studentIds;
}
