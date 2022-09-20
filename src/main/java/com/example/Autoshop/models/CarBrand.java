package com.example.Autoshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min=2, max = 50, message = "Размер данного поля должен быть в диапозене от 2 до 50")
    @NotEmpty(message = "Поле не может быть пустым")
    private String title;

    @OneToMany(mappedBy = "carBrand", fetch = FetchType.EAGER)
    private Collection<SparePart> spareParts;

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

    public CarBrand(String title) {
        this.title = title;
    }

    public CarBrand() {
    }
}
