package com.example.Autoshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min=2, max = 50, message = "Размер данного поля должен быть в диапозене от 2 до 50")
    @NotEmpty(message = "Поле не может быть пустым")
    private String title;

    @NotEmpty(message = "Поле не может быть пустым")
    private double cost;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private User user;

    @ManyToMany
    @JoinTable(name="service_zakaz",
            joinColumns=@JoinColumn(name="service_id"),
            inverseJoinColumns=@JoinColumn(name="zakaz_id"))
    private List<Zakaz> zakazy;

    public Service() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Zakaz> getZakazy() {
        return zakazy;
    }

    public void setZakazy(List<Zakaz> zakazy) {
        this.zakazy = zakazy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Service(String title, double cost, User user) {
        this.title = title;
        this.cost = cost;
        this.user = user;
    }
}
