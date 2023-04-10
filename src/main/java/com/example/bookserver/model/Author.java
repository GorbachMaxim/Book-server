package com.example.bookserver.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String surname;

    private String biography;


}
