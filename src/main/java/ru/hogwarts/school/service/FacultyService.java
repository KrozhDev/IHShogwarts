package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
        return faculty;
    }

    public Faculty findFaculty(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        facultyRepository.save(faculty);
        return faculty;
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> getByColor(String color) {
        return facultyRepository.findAll().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Collection<Faculty> getFacultyByColorOrName(String search) {
        return getFacultyByColorOrName(search, search);
    }

    public Collection<Faculty> getFacultyByColorOrName(String name, String color) {
        return facultyRepository.findFacultiesByNameIsContainingIgnoreCaseOrColorContainingIgnoreCase(name, color);
    }


    public Set<StudentDTO> getFacultysStudents(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new NotFoundException("Нет такого факультета"));
        Set<StudentDTO> students = faculty.getStudents().stream().map(StudentDTO::new).collect(Collectors.toSet());
        return students;
    }

    public String getTheLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length))
                .orElse("No faculties added");
    }
}
