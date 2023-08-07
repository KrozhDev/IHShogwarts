package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultyDTO;
import ru.hogwarts.school.model.StudentDTO;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable long id)
    {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(faculty);
        }
    }

    @PostMapping
    public Faculty createFacultyInfo(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFacultyInfo(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFacultyInfo(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-color")
    public List<Faculty> getByColor(@RequestParam String color) {
        return facultyService.getByColor(color);
    }

    @GetMapping("/by-name-or-color")
    public ResponseEntity<Collection<Faculty>> getByColorOrName(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String color) {
        if (name == null && color == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else if (name == null) {
            Collection<Faculty> foundFaculties = facultyService.getFacultyByColorOrName(color);
            return ResponseEntity.ok(foundFaculties);
        } else
            if (color == null)
            {
            Collection<Faculty> foundFaculties = facultyService.getFacultyByColorOrName(name);
            return ResponseEntity.ok(foundFaculties);
        }
        else {
            Collection<Faculty> foundFaculties = facultyService.getFacultyByColorOrName(name, color);
            return ResponseEntity.ok(foundFaculties);
        }
    }

    @GetMapping("get-facultys-students/{facultyId}")
    public ResponseEntity<Set<StudentDTO>> getStudentsFaculty(@PathVariable Long facultyId) {
        Set<StudentDTO> studentDTO = facultyService.getFacultysStudents(facultyId);
        if (studentDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(studentDTO);
    }


}
