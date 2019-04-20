package com.example.movieapplication.controller;

import com.example.movieapplication.model.Movie;
import com.example.movieapplication.model.MovieRating;
import com.example.movieapplication.model.MovieScore;
import com.example.movieapplication.model.User;
import com.example.movieapplication.service.MovieService;
import com.example.movieapplication.service.RatingService;
import com.example.movieapplication.service.UserDetailsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//RESTFUL API

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/v1")
public class UserDBController {

    @Autowired
    private final MovieService movieService;
    private final UserDetailsLoader userDetailsLoader;
    private final RatingService ratingService;

    public UserDBController(MovieService movieService, UserDetailsLoader userDetailsLoader, RatingService ratingService) {
        this.movieService = movieService;
        this.userDetailsLoader = userDetailsLoader;
        this.ratingService = ratingService;
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
        if (updatingScore.getTotalActualWeightedPoints() != 0) movieRating.setTotalActualWeightedPoints(updatingScore.getTotalActualWeightedPoints());

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


    //THESE ARE TEST API METHODS. NOT TO BE USED IN APPLICATION

    @GetMapping("test/{userId}")
    public ResponseEntity<?> testyMcTesterson(@PathVariable String userId) {
        User user = userDetailsLoader.loadUserWithRatingList(userId);
        List<MovieRating> movieRatings = user.getMovieRatings();
        for(MovieRating movieRating : movieRatings) {
            System.out.println(movieRating.getMovieId());
        }
        return ResponseEntity.ok("cool");
    }

    @DeleteMapping("/test/{movieId}")
    public ResponseEntity<?> testyMcTestersonsBrother(@PathVariable Long movieId) {
        User user = userDetailsLoader.loadUserWithRatingList("travis");
        List<MovieRating> movieRatings = user.getMovieRatings();
        MovieRating movieRatingToDelete = null;
        for (MovieRating movieRating : movieRatings) {
            if (movieId.equals(movieRating.getMovieId())) {
                System.out.println(movieRating.getMovieId());
                movieRatingToDelete = movieRating;
            }
        }
        user.removeFromUserRatingsList(movieRatingToDelete);
        ratingService.delete(movieRatingToDelete);
        System.out.println("movieRatings");
        for (MovieRating movieRating : movieRatings) {
            System.out.println(movieRating.getMovieId());
        }
        user.setMovieRatings(movieRatings);
        System.out.println("userlist ratings");
        for (MovieRating movieRating : user.getMovieRatings()) {
            System.out.println(movieRating.getMovieId());
        }
        userDetailsLoader.saveUser(user);
        return ResponseEntity.ok("cool");
    }

}



