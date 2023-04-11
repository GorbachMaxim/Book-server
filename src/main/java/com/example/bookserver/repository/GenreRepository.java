package com.example.bookserver.repository;

import com.example.bookserver.model.Genre;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByOrderByNameAsc();

}
