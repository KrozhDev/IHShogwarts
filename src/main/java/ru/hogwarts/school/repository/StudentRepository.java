package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.LastFiveStudents;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentDTO;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentsByAgeBetween(int min, int max);

    Collection<Student> findByAge(int age);


    @Query("SELECT COUNT(name) FROM Student ")
    Integer countAllStudents();

    @Query("SELECT AVG(age) FROM Student ")
    Double getAverageAge();

//    @Query(value = "SELECT student.id, name, age from Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
//    Collection<LastFiveStudents> getLastFiveStudents();

    @Query(value = "SELECT id, name, age from student ORDER BY id OFFSET (SELECT count(name) from Student) - 5", nativeQuery = true)
    Collection<LastFiveStudents> getLastFiveStudents();










    //    @Query("Select f.name from faculty f, student s where s.faculty_id = f.id and s.id = " + studentId + "")
//    String findStudentsFacultyById(int studentId);
//
//    Collection<FacultyDTO> findFacultyByStudentId(Long studentId); // почему работает только с коллекциями?


}
