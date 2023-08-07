package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.FacultyDTO;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
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
        studentRepository.deleteById(id);
    }

    public Collection<Student> getByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findStudentsByAgeBetween(min,max);
    }

    public FacultyDTO getStudentsFaculty(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new NotFoundException("Такого студента нет"));
        return new FacultyDTO(student.getFaculty()); //todo ошибка выбрасывается, но до контроллера ничего не доходит, в итоге получаю 500 ошибку
    }
}
