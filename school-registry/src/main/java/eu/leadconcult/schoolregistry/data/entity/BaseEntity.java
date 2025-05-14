package eu.leadconcult.schoolregistry.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(nullable = false, length = 36)
    private UUID id = UUID.randomUUID();
}
