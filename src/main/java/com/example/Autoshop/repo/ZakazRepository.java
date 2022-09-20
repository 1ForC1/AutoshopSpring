package com.example.Autoshop.repo;

import com.example.Autoshop.models.Zakaz;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZakazRepository extends CrudRepository<Zakaz, Long> {
    List<Zakaz> findZakazByAutoshopTitle (String title);
}
