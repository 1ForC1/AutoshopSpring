package com.example.Autoshop.repo;

import com.example.Autoshop.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findBySurnameContains(String surname);
}
