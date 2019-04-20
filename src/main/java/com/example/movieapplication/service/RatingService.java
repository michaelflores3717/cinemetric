package com.example.movieapplication.service;

import com.example.movieapplication.model.Movie;
import com.example.movieapplication.model.MovieRating;
import com.example.movieapplication.repository.Movies;
import com.example.movieapplication.repository.Ratings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private final Ratings ratings;

    public RatingService(Ratings rating) {
        this.ratings = rating;
    }

    public void delete (MovieRating movieRating) {
        ratings.delete(movieRating);
    }

}
