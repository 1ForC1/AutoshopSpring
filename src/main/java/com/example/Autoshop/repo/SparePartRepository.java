package com.example.Autoshop.repo;

import com.example.Autoshop.models.SparePart;
import com.example.Autoshop.models.SparePartType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SparePartRepository extends CrudRepository<SparePart, Long> {
    SparePart findSparePartByTitle(String title);
    List<SparePart> findSparePartByTitleContains(String title);
}
