package ru.hogwarts.school.controller;


import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public Faculty getFacultyInfo(@PathVariable long id) {
        return facultyService.findFaculty(id);
    }

    @PostMapping
    public Faculty createFacultyInfo(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public Faculty editFacultyInfo(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public Faculty deleteFacultyInfo(@PathVariable long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping("by-color")
    public List<Faculty> getByColor(@RequestParam String color) {
        return facultyService.getByColor(color);
    }


}
