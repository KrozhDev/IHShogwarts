package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultyDTO;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentDTO;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;


    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudentInfo() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student1", Student.class))
                .isNotNull();
    }

    @Test
    void testGetFacultyInfo() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty1", Faculty.class))
                .isNotNull();
    }

    @Test
    public void testByAge() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/by-age?age=33", Collection.class))
                .isNotNull();
    }

//    @Test
//    void testGetStudentsFaculty() throws Exception {
//        testCreateStudent();
//        testCreateFaculty();
//        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/get-students-faculty/9", ResponseEntity.class))
//                .isNotNull();
//    }

//    @Test
//    void testDeleteStudent() throws Exception {
//        testCreateStudent();
//        assertThat(this.restTemplate.delete();)
//    }

    @Test
    void testGetByColor() throws Exception {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/by-color?color=red", Faculty.class))
                .isNotNull();
    }

    @Test
    void testGetByColorOrName() throws Exception {
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/by-color-or-name?color=red", Faculty.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/by-color-or-name?name=force", Faculty.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject(
                "http://localhost:" + port + "/faculty/by-color-or-name?name=force&color=red", Faculty.class))
                .isNotNull();
    }

    @Test
    void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty(1L,"force", "red");
//        studentRepository.findById(19L).ifPresent(student -> student.setFaculty(faculty));
        Optional<Student> studentOptional = studentRepository.findById(9L);
        studentOptional.ifPresent(student -> student.setFaculty(faculty));
        studentOptional.ifPresent(student -> studentRepository.save(student));

        assertThat(this.restTemplate
                .postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class))
                .isNotNull();
    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student(1L, "Max Payne", 33);


        assertThat(this.restTemplate
                .postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isNotNull();

    }

    @Test
    void testEditStudent() throws Exception {

        testCreateStudent();

        Student student = studentRepository.findById(1L).get();

        ResponseEntity<Student> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT,
                new HttpEntity<>(student),
                Student.class
        );
        assertThat(exchange.getBody()).isNotNull();
        assertThat(exchange.getBody().getName())
                .isEqualTo(student.getName());

    }



}
