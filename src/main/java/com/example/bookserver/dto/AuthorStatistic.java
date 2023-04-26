package com.example.bookserver.dto;

public class AuthorStatistic {
    private String author;

    private double points;

    public AuthorStatistic(String author, double points) {
        this.author = author;
        this.points = points;
    }

    public String getAuthor() {
        return author;
    }

    public double getPoints() {
        return points;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
