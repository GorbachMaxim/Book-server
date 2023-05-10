package com.example.bookserver.dto.pojo;

import com.example.bookserver.model.Author;
import com.example.bookserver.model.Book;
import com.example.bookserver.model.Genre;
import com.example.bookserver.model.Review;
import lombok.Data;
import org.apache.commons.math3.util.Precision;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private List<ReviewDTO> reviews = new ArrayList<>();

    public BookDTO(Book book){
        this.id = book.getId();
        this.image = book.getImage();
        this.ISBN = book.getISBN();
        this.name = book.getName();
        this.description = book.getDescription();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        avgScore = 0;
        if (book.getReviews().size()>0){
            book.getReviews().forEach(review ->{
                reviews.add(new ReviewDTO(review));
                avgScore += review.getMark();
            });
            Double averageMark = Precision.round(avgScore / reviews.size(), 2);
            avgScore = averageMark.doubleValue();
        }
    }
}
