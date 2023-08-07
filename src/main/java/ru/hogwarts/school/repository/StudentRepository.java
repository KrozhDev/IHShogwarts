package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentsByAgeBetween(int min, int max);

    Collection<Student> findByAge(int age);

    //    @Query("Select f.name from faculty f, student s where s.faculty_id = f.id and s.id = " + studentId + "")
//    String findStudentsFacultyById(int studentId);
//
//    Collection<FacultyDTO> findFacultyByStudentId(Long studentId); // почему работает только с коллекциями?


}
