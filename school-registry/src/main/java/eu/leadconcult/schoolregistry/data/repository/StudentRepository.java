package eu.leadconcult.schoolregistry.data.repository;

import eu.leadconcult.schoolregistry.data.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Page<Student> findByCourses_IdAndAgeGreaterThanEqualOrderByNameAsc(UUID id, int age, Pageable pageable);

    Page<Student> findByCourses_Id(UUID id, Pageable pageable);

    Page<Student> findByGroups_IdOrderByNameAsc(UUID id, Pageable pageable);
}
