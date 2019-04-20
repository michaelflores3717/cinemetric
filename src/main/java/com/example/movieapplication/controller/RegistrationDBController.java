package com.example.movieapplication.controller;

import com.example.movieapplication.model.User;
import com.example.movieapplication.service.UserDetailsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

@RestController
@RequestMapping("/register/")
public class RegistrationDBController {
    @Autowired
    private final UserDetailsLoader userDetailsLoader;

    public RegistrationDBController(UserDetailsLoader userDetailsLoader) {
        this.userDetailsLoader = userDetailsLoader;
    }

    @GetMapping("{username}")
    public ResponseEntity<Boolean> checkIfUsernameIsPresentInDatabase(@PathVariable String username) {
        Boolean userExistsInDataBase = false;
        Iterator<User> allUsers = userDetailsLoader.allUsers().iterator();
        while(allUsers.hasNext()) {
            if (allUsers.next().getUsername().equals(username)) {
                userExistsInDataBase = true;
            }
        }
        return ResponseEntity.ok(userExistsInDataBase);
    }

    @GetMapping("email/{email}")
    public ResponseEntity<Boolean> checkIfEmailIsPresentInDatabase(@PathVariable String email) {
        Boolean emailExistsInDataBase = false;
        Iterator<User> allUsers = userDetailsLoader.allUsers().iterator();
        while(allUsers.hasNext()) {
            if (allUsers.next().getEmail().equals(email)) {
                emailExistsInDataBase = true;
            }
        }
        return ResponseEntity.ok(emailExistsInDataBase);
    }
}
