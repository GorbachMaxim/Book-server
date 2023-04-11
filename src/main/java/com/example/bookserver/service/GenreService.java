package com.example.bookserver.service;

import com.example.bookserver.model.Book;
import com.example.bookserver.model.Genre;
import com.example.bookserver.model.Review;
import com.example.bookserver.repository.BookRepository;
import com.example.bookserver.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public Genre getGenreById(long id){
        return genreRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Genre> getAllGenres(){
        return genreRepository.findByOrderByNameAsc();
    }

    public void saveOrUpdate(Genre genre){
        genreRepository.save(genre);
    }

    public void deleteGenreById(long id){
        genreRepository.deleteById(id);
    }
}
