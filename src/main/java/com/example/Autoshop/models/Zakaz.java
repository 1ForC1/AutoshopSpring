package com.example.Autoshop.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Zakaz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Autoshop autoshop;

    @ManyToMany
    @JoinTable(name="sparePart_zakaz",
            joinColumns=@JoinColumn(name="zakaz_id"),
            inverseJoinColumns=@JoinColumn(name="sparePart_id"))
    private List<SparePart> spareParts;

    @ManyToMany
    @JoinTable(name="service_zakaz",
            joinColumns=@JoinColumn(name="zakaz_id"),
            inverseJoinColumns=@JoinColumn(name="service_id"))
    private List<Service> services;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SparePart> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(List<SparePart> spareParts) {
        this.spareParts = spareParts;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Zakaz(Autoshop autoshop) {
        this.autoshop = autoshop;
    }

    public Autoshop getAutoshop() {
        return autoshop;
    }

    public void setAutoshop(Autoshop autoshop) {
        this.autoshop = autoshop;
    }

    public Zakaz() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Zakaz(User user, Autoshop autoshop) {
        this.user = user;
        this.autoshop = autoshop;
    }
}
