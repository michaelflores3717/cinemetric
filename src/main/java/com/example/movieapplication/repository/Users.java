package com.example.movieapplication.repository;

import com.example.movieapplication.model.User;
import org.springframework.data.repository.CrudRepository;

public interface Users extends CrudRepository<User, Long> {

    User findByUsername(String username);

}