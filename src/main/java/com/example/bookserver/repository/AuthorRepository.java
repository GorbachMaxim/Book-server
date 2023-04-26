package com.example.bookserver.repository;

import com.example.bookserver.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByOrderByNameAsc();


}
