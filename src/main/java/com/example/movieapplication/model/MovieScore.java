package com.example.movieapplication.model;

import javax.persistence.*;

@Entity
@Table(name="movie_score")
public class MovieScore {

    @Id
    @Column(name="score_id")
    @GeneratedValue
    private Long id;

    @Column
    private double finalScore;

    @Column
    private double totalPossiblePoints;

    @Column
    private double totalActualPoints;

    @Column
    private double finalGenreScore;

    @Column
    private double totalPossibleGenrePoints;

    @Column
    private double totalActualGenrePoints;

    @Column
    private double finalWeightedScore;

    @Column
    private double totalPossibleWeightedPoints;

    @Column
    private double totalActualWeightedPoints;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_score_join", nullable = false)
    private Movie movie;

    public MovieScore() {
        finalScore = 0;
        finalGenreScore = 0;
    }

    public MovieScore(Movie movie){
        finalScore = 0;
        finalGenreScore = 0;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
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

    public double getFinalGenreScore() {
        return finalGenreScore;
    }

    public void setFinalGenreScore(double finalGenreScore) {
        this.finalGenreScore = finalGenreScore;
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

    public void calculateFinalScore() {
        finalScore = (totalActualPoints/totalPossiblePoints) * 100;
    }

    public void calculateFinalGenreScore() { finalGenreScore = (totalActualGenrePoints/totalPossibleGenrePoints) * 100; }

    public double getFinalWeightedScore() {
        return finalWeightedScore;
    }

    public void setFinalWeightedScore(double finalWeightedScore) {
        this.finalWeightedScore = finalWeightedScore;
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

    public void calculateFinalWeightedScore() {
        finalWeightedScore = (totalActualWeightedPoints / totalPossibleWeightedPoints) * 100;
    }


}
