package com.example.Autoshop.repo;

import com.example.Autoshop.models.Country;
import com.example.Autoshop.models.Vacancy;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
    List<Vacancy> findByPostContains(String post);
}
