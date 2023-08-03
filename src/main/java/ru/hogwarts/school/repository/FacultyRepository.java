package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findFacultiesByNameIsContainingIgnoreCaseOrColorContainingIgnoreCase(String name, String color);
    Collection<Faculty> findFacultyByColorIgnoreCase(String color);
    Collection<Faculty> findFacultyByNameIgnoreCase(String name);
}
