package com.example.Autoshop.repo;

import com.example.Autoshop.models.Autoshop;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AutoshopRepository extends CrudRepository<Autoshop, Long> {
    Autoshop findAutoshopByTitle(String title);
    List<Autoshop> findAutoshopByTitleContains(String title);
}
