package com.example.Autoshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min=2, max = 50, message = "Размер данного поля должен быть в диапозене от 2 до 50")
    @NotEmpty(message = "Поле не может быть пустым")
    private String post;

    private double salary;

    @Size(min=2, max = 200, message = "Размер данного поля должен быть в диапозене от 2 до 200")
    @NotEmpty(message = "Поле не может быть пустым")
    private String description;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Autoshop autoshop;

    public Vacancy(String post, double salary, String description, Autoshop autoshop) {
        this.post = post;
        this.salary = salary;
        this.description = description;
        this.autoshop = autoshop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Autoshop getAutoshop() {
        return autoshop;
    }

    public void setAutoshop(Autoshop autoshop) {
        this.autoshop = autoshop;
    }

    public Vacancy() {
    }
}
