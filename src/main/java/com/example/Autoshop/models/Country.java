package com.example.Autoshop.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    private Collection<SparePart> spareParts;

    public Country(String title) {
        this.title = title;
    }

    public Country() {
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

    public Collection<SparePart> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(Collection<SparePart> spareParts) {
        this.spareParts = spareParts;
    }
}
