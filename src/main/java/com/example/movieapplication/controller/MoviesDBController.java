package com.example.movieapplication.controller;

import com.example.movieapplication.model.Movie;
import com.example.movieapplication.model.MovieRating;
import com.example.movieapplication.model.User;
import com.example.movieapplication.service.MovieService;
import com.example.movieapplication.service.UserDetailsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies/")
public class MoviesDBController {

    @Autowired
    private final MovieService movieService;
    private final UserDetailsLoader userDetailsLoader;

    public MoviesDBController(MovieService movieService, UserDetailsLoader userDetailsLoader) {
        this.movieService = movieService;
        this.userDetailsLoader = userDetailsLoader;
    }

    @GetMapping("/{username}/get/{movieId}")
    public ResponseEntity<Boolean> checkListOfFavorites (@PathVariable String username, @PathVariable Long movieId) {
        Boolean movieIsInUserFavorites = false;
        User user = userDetailsLoader.loadUserWithMovieList(username);
        if (user == null) {
            ResponseEntity.badRequest().build();
        }
        Optional<Movie> movieOptional = movieService.findById(movieId);
        if (movieOptional.isPresent()) {
            Movie foundMovie = movieOptional.get();
            for (Movie movie : user.getUserMovieList()) {
                if (foundMovie == movie) {
                    movieIsInUserFavorites = true;
                }
            }
        }
        return ResponseEntity.ok(movieIsInUserFavorites);
    }

    @GetMapping("/{username}/get/{movieId}/rating")
    public ResponseEntity<Boolean> checkListOfRatings (@PathVariable String username, @PathVariable Long movieId) {
        Boolean movieIsInUserRatings = false;
        User user = userDetailsLoader.loadUserWithRatingList(username);
        if (user == null) {
            ResponseEntity.badRequest().build();
        }
        List<MovieRating> movieRatings = user.getMovieRatings();
        if (!movieRatings.isEmpty()) {
            for (MovieRating movieRating : movieRatings) {
                if (movieRating.getMovieId().equals(movieId)) {
                    System.out.println(movieRating.getMovieId());
                    System.out.println(movieId);
                    movieIsInUserRatings = true;
                }
            }
        }
        return ResponseEntity.ok(movieIsInUserRatings);
    }

    @DeleteMapping("/{username}/delete/{movieId}")
    public ResponseEntity<?> deleteMovieFromFavorites (@PathVariable String username, @PathVariable Long movieId) {
        User user = userDetailsLoader.loadUserWithMovieList(username);
        if (user == null) {
            ResponseEntity.badRequest().build();
        }
        Optional<Movie> movieOptional = movieService.findById(movieId);
        if (movieOptional.isPresent()) {
            List<Movie> userMovieList = user.getUserMovieList();
            Movie movie = movieOptional.get();
            userMovieList.remove(movie);
            user.setUserMovieList(userMovieList);
        }
        userDetailsLoader.saveUser(user);
        return ResponseEntity.ok("Movie deleted");
    }
}
