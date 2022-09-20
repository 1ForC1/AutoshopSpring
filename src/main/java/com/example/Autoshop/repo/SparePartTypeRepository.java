package com.example.Autoshop.repo;

import com.example.Autoshop.models.SparePartType;
import com.example.Autoshop.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SparePartTypeRepository extends CrudRepository<SparePartType, Long> {
    SparePartType findSparePartTypeByType(String type);
    List<SparePartType> findSparePartTypeByTypeContains(String type);
}
