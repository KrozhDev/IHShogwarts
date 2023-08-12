package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.projection.LastFiveStudents;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);


    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {
        logger.debug("Adding new student {} ...", student);
        studentRepository.save(student);
        return student;
    }

    public Student findStudent(Long id) {
        logger.debug("Searching for a new student by id {} ...", id);
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.debug("Editing student {} ...", student);
        Optional<Student> byId = studentRepository.findById(student.getId());
        byId.ifPresent(student1 -> {
            student1.setAge(student.getAge());
            student1.setName(student.getName());
            student1.setFaculty(student.getFaculty());
        });
        studentRepository.save(student);
        return student;
    }

    public void deleteStudent(Long id) {
        logger.debug("Dismantling student by id {} ...", id);
        studentRepository.deleteById(id);
    }

    public Collection<StudentDTO> getByAge(int age) {
        logger.debug("Searching for a new student by age {} ...", age);
        Set<StudentDTO> students = studentRepository.findByAge(age).stream()
                .map(StudentDTO::new)
                .collect(Collectors.toSet());
        return students;
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.debug("Searching for students between ages {} and {} ...", min, max);
        return studentRepository.findStudentsByAgeBetween(min,max);
    }

    public FacultyDTO getStudentsFaculty(Long studentId) {
        logger.debug("Searching for a students faculty... by id {}", studentId);
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Такого студента нет"));
        return new FacultyDTO(student.getFaculty()); //todo ошибка выбрасывается, но до контроллера ничего не доходит, в итоге получаю 500 ошибку
    }

    public Integer countAllStudents() {
        logger.debug("Counting all students...");
        return studentRepository.countAllStudents();
    }

    public Double getAverageAge() {
        if (studentRepository.countAllStudents() != 0) {
            return studentRepository.getAverageAge();
        } else {
            return 0D;
        }
    }

    public Collection<LastFiveStudents> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}
