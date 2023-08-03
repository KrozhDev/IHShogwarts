package ru.hogwarts.school.model;

import ru.hogwarts.school.model.Faculty;

public class FacultyDTO {

    private Long id;
    private String name;
    private String color;

    public FacultyDTO(Faculty faculty) {
        this.id = faculty.getId();
        this.name = faculty.getName();
        this.color = faculty.getColor();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
