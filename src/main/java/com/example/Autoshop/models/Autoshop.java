package com.example.Autoshop.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Autoshop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String address;

    @OneToMany(mappedBy = "autoshop")
    private Collection<Zakaz> zakazy;

    @OneToMany(mappedBy = "autoshop")
    private Collection<Vacancy> vacancies;

    public Autoshop() {

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<Zakaz> getZakazy() {
        return zakazy;
    }

    public void setZakazy(Collection<Zakaz> zakazy) {
        this.zakazy = zakazy;
    }

    public Collection<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Collection<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public Autoshop(String title, String address) {
        this.title = title;
        this.address = address;
    }
}
