package ru.hogwarts.school.model;

public class StudentDTO {

    private Long id;
    private String name;
    private int age;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.age = student.getAge();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
