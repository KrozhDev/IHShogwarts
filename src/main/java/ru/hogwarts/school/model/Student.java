package ru.hogwarts.school.model;

import nonapi.io.github.classgraph.json.Id;

import javax.persistence.*;


@Entity
public class Student {
    @javax.persistence.Id
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
