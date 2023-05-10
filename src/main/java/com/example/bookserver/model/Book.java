package com.example.bookserver.model;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "book",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "ISBN")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private String ISBN;

    @Column(columnDefinition="VARCHAR")
    private String description;

    @Column(columnDefinition="VARCHAR")
    private String image;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable (name="review",
//            joinColumns=@JoinColumn (name="user_id"),
//            inverseJoinColumns=@JoinColumn(name="book_id"))
//    private List<Review> reviews;


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", ISBN='" + ISBN + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
