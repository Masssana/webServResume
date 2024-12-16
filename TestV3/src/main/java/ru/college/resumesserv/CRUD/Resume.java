package ru.college.resumesserv.CRUD;

import jakarta.persistence.*;
@Entity
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Version
//    private Integer version;

    private Integer course;
    private String name;

    @Column(name = "mid_name")
    private String midName;

    @Column(name = "last_name")
    private String lastName;
    private String specialty;

    @Column(name = "`group`")
    private String group;

    @Column(name = "description")
    private String description;

    public Resume() {

    }

    public Resume(Long id, Integer course, String name, String midName, String lastName, String specialty, String group, String description) {
        this.id = id;
        this.course = course;
        this.name = name;
        this.midName = midName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.group = group;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

/// other setters and getters
}
