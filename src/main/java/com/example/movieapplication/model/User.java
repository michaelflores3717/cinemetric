package com.example.movieapplication.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_movies", joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<Movie> userMovieList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<MovieRating> movieRatings;

    @Column
    private String preferredGenre;

    @Column
    private int horrorPoints;

    @Column
    private int dramaPoints;

    @Column
    private int comedyPoints;

    @Column
    private int romancePoints;

    @Column
    private int sciFiPoints;

    //Get rid of this transient annotation after you get registration restcontroller working
    @Column
    @Transient
    private int miscPoints;

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        userMovieList = copy.userMovieList;
        horrorPoints = copy.horrorPoints;
        dramaPoints = copy.dramaPoints;
        comedyPoints = copy.comedyPoints;
        romancePoints = copy.romancePoints;
        sciFiPoints = copy.sciFiPoints;
        miscPoints = copy.miscPoints;
        preferredGenre = copy.preferredGenre;
    }

    public User() {
        this.horrorPoints = 25;
        this.dramaPoints = 25;
        this.comedyPoints = 25;
        this.romancePoints = 25;
        this.sciFiPoints = 25;
        this.miscPoints = 25;
        this.userMovieList = new ArrayList<>();
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User (Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Movie> getUserMovieList() {
        return userMovieList;
    }

    public void setUserMovieList(List<Movie> movieList) {
        this.userMovieList = movieList;
    }

    public void addToUserMovieList(Movie movie) {
        userMovieList.add(movie);
    }

    public int getHorrorPoints() {
        return horrorPoints;
    }

    public int getDramaPoints() {
        return dramaPoints;
    }

    public int getComedyPoints() {
        return comedyPoints;
    }

    public int getRomancePoints() {
        return romancePoints;
    }

    public int getSciFiPoints() {
        return sciFiPoints;
    }

    public int getMiscPoints() {
        return miscPoints;
    }

    public List<MovieRating> getMovieRatings() {
        return movieRatings;
    }

    public void setMovieRatings(List<MovieRating> movieRatings) {
        this.movieRatings = movieRatings;
    }

    public void addToRatingsList (MovieRating movieRating) {
        movieRatings.add(movieRating);
    }

    public void removeFromUserRatingsList(MovieRating movieRating) {
        movieRatings.remove(movieRating);
    }

    public void setPreferredGenreThroughArrayList() {
        int horrorCounter = 0;
        int dramaCounter = 0;
        int comedyCounter = 0;
        int romanceCounter = 0;
        int sciFiCounter = 0;
        int miscCounter = 0;

        int[] genrePointArrayMinusHorror = {dramaCounter, sciFiCounter, comedyCounter, romanceCounter, miscCounter};
        int[] genrePointsArrayMinusDrama = {horrorCounter, sciFiCounter, comedyCounter, romanceCounter, miscCounter};
        int[] genrePointsArrayMinusSciFi = {horrorCounter, dramaCounter, comedyCounter, romanceCounter, miscCounter};
        int[] genrePointsArrayMinusComedy = {horrorCounter, sciFiCounter, dramaCounter, romanceCounter, miscCounter};
        int[] genrePointsArrayMinusRomance = {horrorCounter, dramaCounter, sciFiCounter, comedyCounter, miscCounter};

        for (Movie movie : userMovieList) {
            if (movie.getGenre().equals("horror") || movie.getGenre().equals("thriller")) {
                horrorCounter++;
            } else if (movie.getGenre().equals("drama") || movie.getGenre().equals("crime")) {
                dramaCounter++;
            } else if (movie.getGenre().equals("comedy")) {
                comedyCounter++;
            } else if (movie.getGenre().equals("romance")) {
                romanceCounter++;
            } else miscCounter++;
        }

        if (checkHighestGenrePoints(horrorCounter, genrePointArrayMinusHorror)) {
            preferredGenre = "horror";
        } else if (checkHighestGenrePoints(dramaCounter, genrePointsArrayMinusDrama)) {
            preferredGenre = "drama";
        } else if (checkHighestGenrePoints(sciFiCounter, genrePointsArrayMinusSciFi)) {
            preferredGenre = "sciFi";
        } else if (checkHighestGenrePoints(comedyCounter, genrePointsArrayMinusComedy)) {
            preferredGenre = "comedy";
        } else if (checkHighestGenrePoints(romanceCounter, genrePointsArrayMinusRomance)) {
            preferredGenre = "romance";
        } else preferredGenre = "miscellaneous";
    }

//    public void setPreferredGenre () {
//        int[] genrePointArrayMinusHorror = {dramaPoints, sciFiPoints, comedyPoints, romancePoints, miscPoints};
//        int[] genrePointsArrayMinusDrama = {horrorPoints, sciFiPoints, comedyPoints, romancePoints, miscPoints};
//        int[] genrePointsArrayMinusSciFi = {horrorPoints, dramaPoints, comedyPoints, romancePoints, miscPoints};
//        int[] genrePointsArrayMinusComedy = {horrorPoints, sciFiPoints, dramaPoints, romancePoints, miscPoints};
//        int[] genrePointsArrayMinusRomance = {horrorPoints, dramaPoints, sciFiPoints, comedyPoints, miscPoints};
//
//        if (checkHighestGenrePoints(horrorPoints, genrePointArrayMinusHorror)) {
//            preferredGenre = "horror";
//        } else if (checkHighestGenrePoints(dramaPoints, genrePointsArrayMinusDrama)) {
//            preferredGenre = "drama";
//        } else if (checkHighestGenrePoints(sciFiPoints, genrePointsArrayMinusSciFi)) {
//            preferredGenre = "sciFi";
//        } else if (checkHighestGenrePoints(comedyPoints, genrePointsArrayMinusComedy)) {
//            preferredGenre = "comedy";
//        } else if (checkHighestGenrePoints(romancePoints, genrePointsArrayMinusRomance)) {
//            preferredGenre = "romance";
//        } else preferredGenre = "miscellaneous";
//    }

    public String getPreferredGenre() {
        return preferredGenre;
    }

    private boolean checkHighestGenrePoints(int genrePoints, int[] genrePointArray) {
        for (int i = 0; i < genrePointArray.length; i++) {
            if (genrePointArray[i] >= genrePoints) {
                return false;
            }
        }
        return true;
    }

    public void setInitialGenrePoints (List <Movie> movieList) {
        for (Movie movie : movieList) {
            if (movie.getGenre().equals("horror")) {
                this.horrorPoints++;
            } else if (movie.getGenre().equals("drama")) {
                this.dramaPoints++;
            } else if (movie.getGenre().equals("comedy")) {
                this.comedyPoints++;
            } else if (movie.getGenre().equals("romance")) {
                this.romancePoints++;
            } else if (movie.getGenre().equals("scifi")) {
                this.sciFiPoints++;
            } else this.miscPoints++;
        }
    }
}
