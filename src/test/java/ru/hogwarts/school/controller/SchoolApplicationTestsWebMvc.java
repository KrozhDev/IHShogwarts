package ru.hogwarts.school.controller;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.*;
import java.util.regex.Matcher;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class SchoolApplicationTestsWebMvc {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private StudentController studentController;
//    @InjectMocks
//    private FacultyController facultyController;

    @Test
    public void testGetStudentInfoAndCreateStudent() throws Exception {

        final Long id = 1L;
        final String name = "name";
        final int age = 19;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        studentObject.put("id", id);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);


        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/student/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));


//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/student")
//                        .param("id", Long.toString(id))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").value(id))
//                .andExpect(jsonPath("$.name").value(name))
//                .andExpect(jsonPath("$.age").value(age));

    }


    @Test
    public void testGetByAge() throws Exception {

        final Integer age = 14;
        List<Student> studentList = new ArrayList<>();
        Student student = new Student(2L, "Harry", age);
        Student student2 = new Student(2L, "Ron", age);
        studentList.add(student);
        studentList.add(student2);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.save(any(Student.class))).thenReturn(student2);
        when(studentRepository.findByAge(any(Integer.class))).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/student/by-age")
                .param("age",Integer.toString(age))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[*].age", Matchers.containsInAnyOrder(14,14)));
    }

    @Test
    public void testGetStudentsFaculty() throws Exception {
        final Long studentId = 3L;
        final Long facultyId = 1L;
        final String facultyName = "force";
        final String facultyColor = "red";
        Student student = new Student(studentId, "Harry", 20);
        student.setFaculty(new Faculty(facultyId, facultyName, facultyColor));

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/student/get-students-faculty/"+ studentId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor));
    }

    @Test
    public void testGetStudentByAgeBetween() throws Exception {
        List<Student> studentList = new ArrayList<>();
        Student student = new Student(2L, "Harry", 14);
        Student student2 = new Student(2L, "Ron", 15);
        studentList.add(student);
        studentList.add(student2);

        when(studentRepository.findStudentsByAgeBetween(14,15)).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/by-age-between")
                        .param("min","14")
                        .param("max","15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[*].age", Matchers.containsInAnyOrder(14,15)));

    }

    @Test
    public void testGetFacultyInfoAndCreateFaculty() throws Exception {


        final Long id = 1L;
        final String name = "name";
        final String color = "color";

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("color", color);
        studentObject.put("id", id);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }
}
