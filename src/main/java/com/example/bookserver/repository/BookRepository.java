package com.example.bookserver.repository;

import com.example.bookserver.model.Book;
import com.example.bookserver.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByOrderByNameAsc();
}
