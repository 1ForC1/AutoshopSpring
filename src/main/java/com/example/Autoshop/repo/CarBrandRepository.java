package com.example.Autoshop.repo;

import com.example.Autoshop.models.CarBrand;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarBrandRepository extends CrudRepository<CarBrand, Long> {
    CarBrand findByTitle(String title);
    List<CarBrand> findByTitleContains(String title);
}
