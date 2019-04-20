package com.example.movieapplication.repository;

import com.example.movieapplication.model.MovieScore;
import org.springframework.data.repository.CrudRepository;

public interface MovieScores extends CrudRepository<MovieScore, Long> {
}
