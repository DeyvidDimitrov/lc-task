package eu.leadconcult.schoolregistry.data.repository;

import eu.leadconcult.schoolregistry.data.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}
