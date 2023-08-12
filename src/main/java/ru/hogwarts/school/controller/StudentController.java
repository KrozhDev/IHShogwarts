package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.projection.LastFiveStudents;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.ok(foundStudent);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/by-age")
    public Collection<StudentDTO> getByAge(@RequestParam int age) {
        return studentService.getByAge(age);
    }

    @GetMapping("/by-age-between")
    public Collection<Student> getByAgeBetween(@RequestParam int min,
                                               @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("get-students-faculty/{studentId}")
    public ResponseEntity<FacultyDTO> getStudentsFaculty(@PathVariable Long studentId) {
        FacultyDTO facultyDTO = studentService.getStudentsFaculty(studentId);
        if (facultyDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(facultyDTO);
    }

//        @GetMapping("get-students-faculty")
//    public Faculty getStudentsFaculty(@RequestParam long studentId) {
//        return studentService.findStudent(studentId).getFaculty();
//    }

    @GetMapping("count")
    public Integer countAllStudents() {
        return studentService.countAllStudents();
    }

    @GetMapping("average-age")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("last-five")
    public Collection<LastFiveStudents> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }
}
