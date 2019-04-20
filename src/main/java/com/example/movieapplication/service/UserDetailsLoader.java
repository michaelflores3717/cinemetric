package com.example.movieapplication.service;

import com.example.movieapplication.model.User;
import com.example.movieapplication.model.UserWithRoles;
import com.example.movieapplication.repository.Users;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsLoader implements UserDetailsService {

    private final Users users;

    public UserDetailsLoader(Users users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }
        return new UserWithRoles(user);
    }

    @Transactional
    public User loadUserWithMovieList(String username) {
        User user = users.findByUsername(username);
        Hibernate.initialize(user.getUserMovieList());
        return user;
    }

    @Transactional
    public User loadUserWithRatingList(String username) {
        User user = users.findByUsername(username);
        Hibernate.initialize(user.getMovieRatings());
        return user;
    }

    public User loadUserThroughObject(Object obj) {
        UserWithRoles userWithRoles = (UserWithRoles)obj;
        User user = users.findByUsername(userWithRoles.getUsername());
        return user;
        }

    public User saveUser(User user) {
        return users.save(user);
    }

    public Iterable<User> allUsers () {
        return users.findAll();
    }

}



