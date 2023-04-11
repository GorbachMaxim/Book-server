package com.example.bookserver.service;

import com.example.bookserver.model.Book;
import com.example.bookserver.model.Review;
import com.example.bookserver.model.User;
import com.example.bookserver.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class ReviewService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserDetailsServiceImpl userService;


    public void addReview(HttpServletRequest request, Review review, long id){
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        review.setBook(book);
        User user = userService.getUserFromJWT(request);
        review.setUser(user);
        bookRepository.save(book);
    }
}
