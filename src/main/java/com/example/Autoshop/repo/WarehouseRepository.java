package com.example.Autoshop.repo;

import com.example.Autoshop.models.Warehouse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {
    Warehouse findByAddress(String address);
    List<Warehouse> findByAddressContains(String address);
}
