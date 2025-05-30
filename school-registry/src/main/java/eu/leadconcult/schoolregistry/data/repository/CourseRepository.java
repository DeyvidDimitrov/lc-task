package eu.leadconcult.schoolregistry.data.repository;

import eu.leadconcult.schoolregistry.data.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByTeacher_Id(UUID id);
}
