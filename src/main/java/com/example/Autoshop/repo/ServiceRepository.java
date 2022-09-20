package com.example.Autoshop.repo;

import com.example.Autoshop.models.Country;
import com.example.Autoshop.models.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceRepository extends CrudRepository<Service, Long> {
    List<Service> findByTitleContains(String title);
}
