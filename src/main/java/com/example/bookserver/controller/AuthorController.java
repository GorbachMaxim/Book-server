package com.example.bookserver.controller;

import com.example.bookserver.dto.MessageResponse;
import com.example.bookserver.model.Author;
import com.example.bookserver.model.Genre;
import com.example.bookserver.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/author")
@CrossOrigin
public class AuthorController {
    @Autowired
    private AuthorService authorService;


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Author> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return authors;
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Author getAuthor(@PathVariable long id){
        Author author = authorService.getAuthorById(id);
        return author;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAuthor(@RequestBody @Valid Author Author) {
        authorService.saveOrUpdate(Author);
        return ResponseEntity.ok(new MessageResponse("Author CREATED"));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAuthor(@RequestBody @Valid Author author){
        Author author1 = authorService.getAuthorById(author.getId());

        if(author1 == null){
            return ResponseEntity.badRequest().body(new MessageResponse("No such Author"));
        }

        if(!author.getName().isBlank())
            author1.setName(author.getName());
        if(!author.getSurname().isBlank())
            author1.setSurname(author.getSurname());
        if(!author.getBiography().isBlank())
            author1.setBiography(author.getBiography());
        if(!author.getImage().isBlank())
            author1.setImage(author.getImage());

//        author1.setName(author.getName());
//        author1.setSurname(author.getSurname());
//        author1.setBiography(author.getBiography());
//        author1.setImage(author.getImage());

        authorService.saveOrUpdate(author1);
        return ResponseEntity.ok(new MessageResponse("Author UPDATED"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteGenre(@PathVariable long id){
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok(new MessageResponse("Author DELETED"));
    }
}
