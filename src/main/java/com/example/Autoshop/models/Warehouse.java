package com.example.Autoshop.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    private String address;

    public Warehouse() {
    }

    public Warehouse(String address, Supplier supplier) {
        this.address = address;
        this.supplier = supplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<SparePart> getSpareParts() {
        return spareParts;
    }

    public void setSpareParts(Collection<SparePart> spareParts) {
        this.spareParts = spareParts;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER)
    private Collection<SparePart> spareParts;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Supplier supplier;
}
