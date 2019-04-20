package com.example.movieapplication.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;

import javax.persistence.*;

@Entity
@Table(name="ratings")
public class MovieRating {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long movieId;

    @Column
    private double totalPossiblePoints;

    @Column
    private double totalActualPoints;

    @Column
    private double totalPossibleGenrePoints;

    @Column
    private double totalActualGenrePoints;

    @Column
    private double totalPossibleWeightedPoints;

    @Column
    private double totalActualWeightedPoints;

    @ManyToOne
    @JoinColumn(name = "user_rating")
    private User user;

    public MovieRating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public double getTotalPossiblePoints() {
        return totalPossiblePoints;
    }

    public void setTotalPossiblePoints(double totalPossiblePoints) {
        this.totalPossiblePoints = totalPossiblePoints;
    }

    public double getTotalActualPoints() {
        return totalActualPoints;
    }

    public void setTotalActualPoints(double totalActualPoints) {
        this.totalActualPoints = totalActualPoints;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalPossibleGenrePoints() {
        return totalPossibleGenrePoints;
    }

    public void setTotalPossibleGenrePoints(double totalPossibleGenrePoints) {
        this.totalPossibleGenrePoints = totalPossibleGenrePoints;
    }

    public double getTotalActualGenrePoints() {
        return totalActualGenrePoints;
    }

    public void setTotalActualGenrePoints(double totalActualGenrePoints) {
        this.totalActualGenrePoints = totalActualGenrePoints;
    }

    public double getTotalPossibleWeightedPoints() {
        return totalPossibleWeightedPoints;
    }

    public void setTotalPossibleWeightedPoints(double totalPossibleWeightedPoints) {
        this.totalPossibleWeightedPoints = totalPossibleWeightedPoints;
    }

    public double getTotalActualWeightedPoints() {
        return totalActualWeightedPoints;
    }

    public void setTotalActualWeightedPoints(double totalActualWeightedPoints) {
        this.totalActualWeightedPoints = totalActualWeightedPoints;
    }
}
