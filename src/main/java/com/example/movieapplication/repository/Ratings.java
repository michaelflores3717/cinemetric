package com.example.movieapplication.repository;

import com.example.movieapplication.model.MovieRating;
import org.springframework.data.repository.CrudRepository;

public interface Ratings extends CrudRepository<MovieRating, Long> {
}
