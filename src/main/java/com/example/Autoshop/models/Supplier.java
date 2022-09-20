package com.example.Autoshop.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String address;

    public Supplier(String title, String address) {
        this.title = title;
        this.address = address;
    }

    public Supplier() {
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

    public Collection<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Collection<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    private Collection<Warehouse> warehouses;
}
