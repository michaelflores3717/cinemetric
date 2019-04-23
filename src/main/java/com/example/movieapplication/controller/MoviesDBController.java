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

@RestController
@RequestMapping("/movies/")
public class MoviesDBController {

    @Autowired
    private final MovieService movieService;
    private final UserDetailsLoader userDetailsLoader;
    private final RatingService ratingService;

    public MoviesDBController(MovieService movieService, UserDetailsLoader userDetailsLoader, RatingService ratingService) {
        this.movieService = movieService;
        this.userDetailsLoader = userDetailsLoader;
        this.ratingService = ratingService;
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

    @DeleteMapping("/{username}/delete/rating/{movieId}")
    public ResponseEntity<?> deleteMovieRating (@PathVariable String username, @PathVariable Long movieId) {
        User user = userDetailsLoader.loadUserWithRatingList(username);
        if (user == null) {
            ResponseEntity.badRequest().build();
        }
        Optional<Movie> movieOptional = movieService.findById(movieId);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            MovieScore movieScore = movie.getMovieScore();
            System.out.println("movie score total possible" + movieScore.getTotalPossibleWeightedPoints());
            System.out.println("movie score actual weighted" + movieScore.getTotalActualWeightedPoints());
            List<MovieRating> userMovieRatingList = user.getMovieRatings();
            MovieRating movieRatingToRemove = null;
            for (MovieRating movieRating : userMovieRatingList) {
                if (movieRating.getMovieId().equals(movie.getId())) {
                    System.out.println("movie rating total actual weighted" + movieRating.getTotalActualWeightedPoints());
                    System.out.println("movie rating total possible weighted" + movieRating.getTotalPossibleWeightedPoints());
                    movieScore.setTotalActualWeightedPoints(movieScore.getTotalActualWeightedPoints() - movieRating.getTotalActualWeightedPoints());
                    movieScore.setTotalPossibleWeightedPoints(movieScore.getTotalPossibleWeightedPoints() - movieRating.getTotalPossibleWeightedPoints());
                    System.out.println("movie score total actual weighted after change" + movieScore.getTotalActualWeightedPoints());
                    System.out.println("movie score total possible  weighted after change" + movieScore.getTotalPossibleWeightedPoints());
                    movieScore.setTotalActualPoints(movieScore.getTotalActualPoints() - movieRating.getTotalActualPoints());
                    movieScore.setTotalPossiblePoints(movieScore.getTotalPossiblePoints() - movieRating.getTotalPossiblePoints());
                    movieScore.setTotalActualGenrePoints(movieScore.getTotalActualGenrePoints() - movieRating.getTotalActualGenrePoints());
                    movieScore.setTotalPossibleGenrePoints(movieScore.getTotalActualGenrePoints() - movieRating.getTotalActualGenrePoints());

                    if (movieScore.getTotalPossibleGenrePoints() == 0) {
                        movieScore.setFinalGenreScore(0);
                    } else if (movieScore.getTotalPossibleGenrePoints() != 0) {
                        movieScore.calculateFinalGenreScore();
                    }
                    if (movieScore.getTotalPossibleWeightedPoints() == 0) {
                        movieScore.setFinalWeightedScore(0);
                    } else if (movieScore.getTotalPossibleWeightedPoints() != 0) {
                        movieScore.calculateFinalWeightedScore();
                    }
                    if (movieScore.getTotalPossiblePoints() == 0) {
                        movieScore.setFinalScore(0);
                    } else if (movieScore.getTotalPossiblePoints() != 0) {
                        movieScore.calculateFinalScore();
                    }
                    movieRatingToRemove = movieRating;

                }
            }

            movie.setMovieScore(movieScore);

            if (movieRatingToRemove != null) {
                userMovieRatingList.remove(movieRatingToRemove);
                user.setMovieRatings(userMovieRatingList);
                ratingService.delete(movieRatingToRemove);
            }

            movieService.save(movie);
            userDetailsLoader.saveUser(user);
        }

        return ResponseEntity.ok("Movie rating removed");
    }

    @GetMapping("favorites/{userId}")
    public ResponseEntity<List<Movie>> returnFavoritesMoviesList(@PathVariable String userId) {
        User user = userDetailsLoader.loadUserWithMovieList(userId);
        List<Movie> movieList = user.getUserMovieList();
        for (Movie movie : movieList) {
            System.out.println(movie.getTitle());
        }
        return ResponseEntity.ok(movieList);
    }
}
