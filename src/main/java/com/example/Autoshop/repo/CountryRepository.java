package com.example.Autoshop.repo;

import com.example.Autoshop.models.Country;
import com.example.Autoshop.models.SparePartType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Long> {
    Country findByTitle(String title);
    List<Country> findByTitleContains(String title);
}
