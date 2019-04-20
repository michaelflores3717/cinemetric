package com.example.movieapplication.controller;

import com.example.movieapplication.model.Movie;
import com.example.movieapplication.model.User;
import com.example.movieapplication.model.UserTransporter;
import com.example.movieapplication.repository.Movies;
import com.example.movieapplication.repository.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    private Users users;
    private PasswordEncoder passwordEncoder;
    private Movies movies;
    private UserTransporter transporter = new UserTransporter();

    public UserController(Users users, PasswordEncoder passwordEncoder, Movies movies) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.movies = movies;
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registrationPost (@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        transporter.setUser(user);
        return "redirect:register/movies";
    }

    //code refactored to take in iterated list. this is technically done. only update we need to make
    // is to insert correct movies from database. this will be a constant

    @GetMapping("register/movies")
    public String userSelectsFilms(Model model) {
        List<Long> movieLongList = new ArrayList<Long>(Arrays.asList(289L, 595L));
        Iterable<Movie> movieStarters = movies.findAllById(movieLongList);
        model.addAttribute("movieSignUpList", movieStarters);
        return "register-movies";
    }

    // still need to rewrite this code to find all by id in movieIdList and insert into StarterList
    // to insert into user object and save
    //UPDATE: code has been rewritten to do just this. cleaner.

    @PostMapping("register/movies")
    public String userFilmsPost(@RequestParam ("movieIdList") Long[] movieIdList) {
        User user = transporter.getUser();
        List<Movie> movieStarterList = new ArrayList<>();
        List<Long> movieIdStarter = new ArrayList<>(Arrays.asList(movieIdList));
        Iterable<Movie> movieIterable = movies.findAllById(movieIdStarter);
        for (Movie movie : movieIterable) {
            movieStarterList.add(movie);
        }
        user.setInitialGenrePoints(movieStarterList);
        user.setUserMovieList(movieStarterList);
        user.setPreferredGenreThroughArrayList();
        users.save(user);
        return "redirect:/login";
    }

}


