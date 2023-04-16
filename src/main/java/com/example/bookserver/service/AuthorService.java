package com.example.bookserver.service;

import com.example.bookserver.model.Author;
import com.example.bookserver.model.Genre;
import com.example.bookserver.repository.AuthorRepository;
import com.example.bookserver.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author getAuthorById(long id){
        return authorRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Author> getAllAuthors(){
        return authorRepository.findByOrderByNameAsc();
    }

    public void saveOrUpdate(Author author){
        authorRepository.save(author);
    }

    public void deleteAuthorById(long id){
        authorRepository.deleteById(id);
    }
}
