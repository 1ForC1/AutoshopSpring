package com.example.Autoshop.repo;

import com.example.Autoshop.models.Brand;
import com.example.Autoshop.models.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BrandRepository extends CrudRepository<Brand, Long> {
    Brand findByTitle(String title);
    List<Brand> findByTitleContains(String title);
}
