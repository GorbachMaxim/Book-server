package com.example.bookserver.controller;


import com.example.bookserver.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
@CrossOrigin
public class BookController {

    private final BookRepository bookRepository;


}
