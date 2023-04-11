package com.example.bookserver.dto.pojo;

import com.example.bookserver.model.Author;
import com.example.bookserver.model.Review;

import javax.persistence.*;
import java.util.Set;

public class BookDTO {
    private long id;

    private String name;

    private Author author;

    private String ISBN;

    private String image;

    private Set<Review> reviews;
}
