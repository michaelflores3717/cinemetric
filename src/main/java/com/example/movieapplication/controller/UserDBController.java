package com.example.movieapplication.controller;

import com.example.movieapplication.model.Movie;
import com.example.movieapplication.model.MovieRating;
import com.example.movieapplication.model.MovieScore;
import com.example.movieapplication.model.User;
import com.example.movieapplication.service.MovieService;
import com.example.movieapplication.service.UserDetailsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//RESTFUL API

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/v1")
public class UserDBController {

    @Autowired
    private final MovieService movieService;
    private final UserDetailsLoader userDetailsLoader;

    public UserDBController(MovieService movieService, UserDetailsLoader userDetailsLoader) {
        this.movieService = movieService;
        this.userDetailsLoader = userDetailsLoader;
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> findById(@PathVariable Long movieId) {
        Optional<Movie> movieOptional = movieService.findById(movieId);
        if (!movieOptional.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(movieOptional.get());
    }

    @PatchMapping("/{username}/add/{movieId}")
    public ResponseEntity<?> addMovieToUserFavorites(@PathVariable String username, @PathVariable Long movieId) {
        User user = userDetailsLoader.loadUserWithMovieList(username);
        if (user == null) {
            ResponseEntity.badRequest().build();
        }
        Optional<Movie> movieOptional = movieService.findById(movieId);
        Movie foundMovie = movieOptional.get();
        user.addToUserMovieList(foundMovie);
        userDetailsLoader.saveUser(user);
        return ResponseEntity.ok("Movie saved to user list.");
    }

    @PatchMapping("/{username}/{movieId}/rating/{movieGenreMatch}")
    public ResponseEntity<?> updateRating(@PathVariable Long movieId, @PathVariable boolean movieGenreMatch, @RequestBody MovieScore updatingScore, @PathVariable String username) {
        User user = userDetailsLoader.loadUserWithRatingList(username);
        if (user == null) {
            ResponseEntity.badRequest().build();
        }

        Optional<Movie>movieOptional = movieService.findById(movieId);
        if (!movieOptional.isPresent()) {
            ResponseEntity.badRequest().build();
        }

        MovieRating movieRating = new MovieRating();

        Movie movie = movieOptional.get();

        System.out.println(movieId);
        movieRating.setMovieId(movieId);

        MovieScore movieScore = movie.getMovieScore();
        if (updatingScore.getTotalPossiblePoints() != 0) movieScore.setTotalPossiblePoints(movieScore.getTotalPossiblePoints() + updatingScore.getTotalPossiblePoints());
        if (updatingScore.getTotalPossiblePoints() != 0) movieRating.setTotalPossiblePoints(updatingScore.getTotalPossiblePoints());
        if (updatingScore.getTotalActualPoints() != 0) movieScore.setTotalActualPoints(movieScore.getTotalActualPoints() + updatingScore.getTotalActualPoints());
        if (updatingScore.getTotalActualPoints() != 0) movieRating.setTotalActualPoints(updatingScore.getTotalActualPoints());
        if (updatingScore.getTotalPossibleWeightedPoints() != 0) movieScore.setTotalPossibleWeightedPoints(movieScore.getTotalPossibleWeightedPoints() + updatingScore.getTotalPossibleWeightedPoints());
        if (updatingScore.getTotalPossibleWeightedPoints() !=0) movieRating.setTotalPossibleWeightedPoints(updatingScore.getTotalPossibleWeightedPoints());
        if (updatingScore.getTotalActualWeightedPoints() != 0) movieScore.setTotalActualWeightedPoints(movieScore.getTotalActualWeightedPoints() + updatingScore.getTotalActualWeightedPoints());
        if (updatingScore.getTotalActualWeightedPoints() != 0) movieRating.setTotalActualWeightedPoints(updatingScore.getTotalPossibleWeightedPoints());

        if (movieGenreMatch) {
            if (updatingScore.getTotalPossibleGenrePoints() != 0) movieScore.setTotalPossibleGenrePoints(movieScore.getTotalPossibleGenrePoints() + updatingScore.getTotalPossibleGenrePoints());
            if (updatingScore.getTotalPossibleGenrePoints() != 0) movieRating.setTotalPossibleGenrePoints(updatingScore.getTotalPossibleGenrePoints());
            if (updatingScore.getTotalActualGenrePoints() != 0) movieScore.setTotalActualGenrePoints(movieScore.getTotalActualGenrePoints() + updatingScore.getTotalActualGenrePoints());
            if (updatingScore.getTotalActualGenrePoints() != 0) movieRating.setTotalActualGenrePoints(updatingScore.getTotalActualGenrePoints());
            movieScore.calculateFinalGenreScore();
        }


        user.addToRatingsList(movieRating);
        movieRating.setUser(user);

        movieScore.calculateFinalScore();
        movieScore.calculateFinalWeightedScore();
        movie.setMovieScore(movieScore);

        userDetailsLoader.saveUser(user);
        movieService.save(movie);
        return ResponseEntity.ok("Movie rating saved.");
    }


    @PatchMapping("/test/{movieId}")
    public ResponseEntity<Movie> update(@PathVariable Long movieId, @RequestBody Movie updatingMovie) {
        Optional<Movie> movieOptional = movieService.findById(movieId);
        if (!movieOptional.isPresent()) {
            ResponseEntity.badRequest().build();
        }

        Movie movie = movieOptional.get();
        if (updatingMovie.getTitle() != null) movie.setTitle(updatingMovie.getTitle());

        return ResponseEntity.ok(movieService.save(movie));
    }

}



