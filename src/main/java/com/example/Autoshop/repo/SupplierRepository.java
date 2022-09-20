package com.example.Autoshop.repo;

import com.example.Autoshop.models.Supplier;
import com.example.Autoshop.models.Warehouse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    Supplier findByTitle(String title);
    List<Supplier> findByTitleContains(String title);
}
