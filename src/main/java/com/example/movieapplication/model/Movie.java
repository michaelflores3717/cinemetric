package com.example.movieapplication.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="movies")
public class Movie {

    @Id
    @Column(name = "movie_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String genre;

    @Column
    private String posterUrl;

    @ManyToMany(mappedBy = "userMovieList")
    private List<User> users = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "movie")
    private MovieScore movieScore;

    public Movie () {
    }

    public Movie(Long id, String title, String description, String genre, String posterUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.posterUrl = posterUrl;

    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public MovieScore getMovieScore() {
        return movieScore;
    }

    public void setMovieScore(MovieScore movieScore) {
        this.movieScore = movieScore;
    }

}
