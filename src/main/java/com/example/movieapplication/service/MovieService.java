package com.example.movieapplication.service;

import com.example.movieapplication.model.Movie;
import com.example.movieapplication.repository.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

    @Service
    public class MovieService {

        @Autowired
        private final Movies movies;

        public MovieService(Movies movies) {
            this.movies = movies;
        }

        public Optional<Movie> findById(Long id) {
            return movies.findById(id);
        }

        public Movie save(Movie movie) {
            return movies.save(movie);
        }

    }

