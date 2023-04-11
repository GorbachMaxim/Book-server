package com.example.bookserver.controller;

import com.example.bookserver.dto.MessageResponse;
import com.example.bookserver.dto.SignupRequest;
import com.example.bookserver.dto.pojo.UserDTO;
import com.example.bookserver.model.ERole;
import com.example.bookserver.model.Genre;
import com.example.bookserver.model.Role;
import com.example.bookserver.model.User;
import com.example.bookserver.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/genre")
@CrossOrigin
public class GenreController {

    @Autowired
    private GenreService genreService;


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Genre> getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        return genres;
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Genre getGenre(@PathVariable long id){
        Genre genre = genreService.getGenreById(id);
        return genre;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addGenre(@RequestBody @Valid Genre genre) {
        genreService.saveOrUpdate(genre);
        return ResponseEntity.ok(new MessageResponse("Genre CREATED"));
    }

    @PutMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateGenre(@RequestBody @Valid Genre genre){
        Genre genre1 = genreService.getGenreById(genre.getId());
        if(!genre.getName().isBlank())
            genre1.setName(genre.getName());
        if(!genre.getDescription().isBlank())
            genre1.setDescription(genre.getDescription());
        if(!genre.getImage().isBlank())
            genre1.setImage(genre.getImage());
        genreService.saveOrUpdate(genre1);
        return ResponseEntity.ok(new MessageResponse("Genre UPDATED"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteGenre(@PathVariable long id){
        genreService.deleteGenreById(id);
        return ResponseEntity.ok(new MessageResponse("Genre DELETED"));
    }

}
