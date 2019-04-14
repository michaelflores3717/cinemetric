package com.example.movieapplication.controller;

import com.example.movieapplication.model.Movie;
import com.example.movieapplication.model.MovieScore;
import com.example.movieapplication.model.User;
import com.example.movieapplication.model.UserWithRoles;
import com.example.movieapplication.repository.Movies;
import com.example.movieapplication.service.UserDetailsLoader;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
public class MovieController {

    @Autowired
    private Movies movies;
    private HttpResponse<JsonNode> jsonNodeHttpResponse;
    private static final String baseURL = "https://api.themoviedb.org/3/movie/";
    private static final String apiKey = "?api_key=49990b53c193c05b1ce005f26fbf50c1&language=en-US";
    private UserDetailsLoader userDetailsLoader;

    public MovieController (UserDetailsLoader userDetailsLoader) {
        this.userDetailsLoader = userDetailsLoader;
    }

    @RequestMapping("movies/{movie}")
    public String getMoviePage(Model model, @PathVariable("movie") String movie) {

        String[] movieUrlStringArray = movie.split("-");
        Long movieUrlLongId = Long.parseLong(movieUrlStringArray[0]);
        Optional<Movie> foundOptionalMovie = movies.findById(movieUrlLongId);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserWithRoles) {
            User user = userDetailsLoader.loadUserThroughObject(principal);
            model.addAttribute("user", user);
        }

        if (!foundOptionalMovie.isPresent()) {

            try {
                jsonNodeHttpResponse = Unirest.get(baseURL + "{movieId}" + apiKey)
                        .routeParam("movieId", movieUrlLongId.toString())
                        .asJson();
            } catch (UnirestException e) {
                e.printStackTrace();
            }

            JSONObject movieObject = jsonNodeHttpResponse.getBody().getObject();
            Integer movieIdInt = movieObject.getInt("id");
            Long movieId = Long.parseLong(movieIdInt.toString());
            String movieImageUrl = movieObject.getString("poster_path");
            String movieTitle = movieObject.getString("title");
            String movieDescription = movieObject.getString("overview");
            String movieGenre = movieObject.getJSONArray("genres").getJSONObject(0).getString("name").toLowerCase();
            MovieScore movieScore = new MovieScore();
            Movie movieObjectToAddToDB = new Movie(movieId, movieTitle, movieDescription, movieGenre, movieImageUrl);
            movieObjectToAddToDB.setMovieScore(movieScore);
            movieScore.setMovie(movieObjectToAddToDB);
            movies.save(movieObjectToAddToDB);

            model.addAttribute("movie", movieObjectToAddToDB);
            return "movie-page";

        } else {

            Movie foundMovie = foundOptionalMovie.get();
            model.addAttribute("movie", foundMovie);

            return "movie-page";

        }
    }
}

