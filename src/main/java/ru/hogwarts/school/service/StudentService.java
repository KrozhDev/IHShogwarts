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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    static int index = 0;


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
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    public FacultyDTO getStudentsFaculty(Long studentId) {
        logger.debug("Searching for a students faculty... by id {}", studentId);
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Такого студента нет"));
        return new FacultyDTO(student.getFaculty()); //todo ошибка выбрасывается, но до контроллера ничего не доходит, в итоге получаю 500 ошибку
    }

    public Integer countAllStudents() {
        logger.debug("Counting all students...");
        return studentRepository.countAllStudents();
    }

//    public Double getAverageAge() {
//        if (studentRepository.countAllStudents() != 0) {
//            return studentRepository.getAverageAge();
//        } else {
//            return 0D;
//        }
//    }

    public Double getAverageAge() {
        return studentRepository.findAll().stream()
                .map(Student::getAge)
                .mapToInt(s -> s)
                .average().orElse(0);

    }


    public Collection<LastFiveStudents> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

    public Collection<String> getNamesFromAInUppercase() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());

    }

    public Integer getSomeInteger() {
        long startTime = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        long endTime = System.currentTimeMillis();
        logger.debug("The operation takes {} ms", endTime - startTime);
        return sum;


    }

    public void getStudentsNamesByParallelStreams1() {
        List<String> studentList = studentRepository.findAll().stream()
                .map(Student::getName)
                .sorted()
                .toList();


        logger.info("Контрольный вывод списка имен");
        studentList.forEach(System.out::println);


        logger.info("Запускаю потоки");
        Thread thread1 = new Thread(() -> print(studentList, 0, "Поток 1 работает"));
        thread1.start();
        Thread thread2 = new Thread(() -> print(studentList, 2, "Поток 2 работает"));
        thread2.start();
        Thread thread3 = new Thread(() -> print(studentList, 4,"Поток 3 работает"));
        thread3.start();
    }

    private void print(List<String> names, int index, String name) {
        System.out.println(name);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(names.get(index++));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(names.get(index));
    }

    public void getStudentsNamesByParallelStreams2() {



        List<String> studentList = studentRepository.findAll().stream()
                .map(Student::getName)
                .sorted()
                .toList();


        logger.info("Контрольный вывод списка имен");
        studentList.forEach(System.out::println);


        logger.info("Запускаю потоки");

            Thread thread1 = new Thread(() -> print2(studentList, "Поток 1 работает"));
            thread1.start();
            Thread thread2 = new Thread(() -> print2(studentList,"Поток 2 работает"));
            thread2.start();
            Thread thread3 = new Thread(() -> print2(studentList,"Поток 3 работает"));
            thread3.start();
            index = 0;

    }

    private synchronized void print2(List<String> names, String name) {
        System.out.println(name);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(names.get(index++));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(names.get(index++));
    }
}
