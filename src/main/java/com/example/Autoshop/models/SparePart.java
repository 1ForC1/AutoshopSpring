package com.example.Autoshop.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class SparePart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public SparePart(String title, SparePartType sparePartType, Country country, Brand brand, CarBrand carBrand, Warehouse warehouse) {
        this.title = title;
        this.sparePartType = sparePartType;
        this.country = country;
        this.brand = brand;
        this.carBrand = carBrand;
        this.warehouse = warehouse;
    }

    public SparePart() {
    }

    private String title;

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

    public SparePartType getSparePartType() {
        return sparePartType;
    }

    public void setSparePartType(SparePartType sparePartType) {
        this.sparePartType = sparePartType;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private SparePartType sparePartType;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Country country;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Brand brand;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private CarBrand carBrand;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private Warehouse warehouse;

    @ManyToMany
    @JoinTable(name="sparePart_zakaz",
            joinColumns=@JoinColumn(name="sparePart_id"),
            inverseJoinColumns=@JoinColumn(name="zakaz_id"))
    private List<Zakaz> zakazy;
}
