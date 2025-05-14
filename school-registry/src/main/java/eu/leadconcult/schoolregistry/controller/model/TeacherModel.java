package eu.leadconcult.schoolregistry.controller.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherModel {

    @NotBlank
    private String name;

    @Min(19)
    @Max(100)
    private Integer age;
}
