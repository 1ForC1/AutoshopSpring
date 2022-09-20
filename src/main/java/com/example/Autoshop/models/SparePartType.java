package com.example.Autoshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
public class SparePartType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min=2, max = 50, message = "Размер данного поля должен быть в диапозене от 2 до 50")
    @NotEmpty(message = "Поле не может быть пустым")
    private String type;

    public SparePartType(String type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "sparePartType", fetch = FetchType.EAGER)
    private Collection<SparePart> spareParts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<SparePart> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(Collection<SparePart> spareParts) {
        this.spareParts = spareParts;
    }

    public SparePartType() {
    }
}
