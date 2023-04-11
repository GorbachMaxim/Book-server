package com.example.bookserver.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @Column(columnDefinition="VARCHAR")
    private String image;

    @Column(columnDefinition="VARCHAR")
    private String description;
}
