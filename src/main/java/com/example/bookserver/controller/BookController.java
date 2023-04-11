package com.example.bookserver.controller;


import com.example.bookserver.model.User;
import com.example.bookserver.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {

//    @Autowired
//    private BookRepository bookRepository;

//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public User getUser(@PathVariable long id){
//        User user = userService.getUserById(id);
//        user.setReadBooks(null);
//        return user;
//    }


}
