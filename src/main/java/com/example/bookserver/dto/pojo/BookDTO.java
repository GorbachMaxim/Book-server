package com.example.bookserver.dto.pojo;

import com.example.bookserver.model.Author;
import com.example.bookserver.model.Book;
import com.example.bookserver.model.Genre;
import com.example.bookserver.model.Review;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
public class BookDTO {
    private long id;

    private String name;

    private Author author;

    private Genre genre;

    private String description;

    private String ISBN;

    private String image;

    private double avgScore;

    private Set<ReviewDTO> reviews;

    public BookDTO(Book book){
        this.id = book.getId();
        this.image = book.getImage();
        this.ISBN = book.getISBN();
        this.name = book.getName();
        this.description = book.getDescription();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        avgScore = 0;
        book.getReviews().forEach(review ->{
            reviews.add(new ReviewDTO(review));
            avgScore += review.getMark();
        });
        avgScore /= reviews.size();
    }
}
