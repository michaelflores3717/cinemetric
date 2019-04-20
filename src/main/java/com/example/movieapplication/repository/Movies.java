package com.example.movieapplication.repository;

import com.example.movieapplication.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface Movies extends CrudRepository<Movie, Long> {

    Movie findByTitle(String title);
}

