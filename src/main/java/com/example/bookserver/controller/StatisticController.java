package com.example.bookserver.controller;

import com.example.bookserver.dto.AuthorStatistic;
import com.example.bookserver.dto.pojo.BookDTO;
import com.example.bookserver.model.Author;
import com.example.bookserver.model.Book;
import com.example.bookserver.model.Review;
import com.example.bookserver.model.User;
import com.example.bookserver.service.AuthorService;
import com.example.bookserver.service.BookService;
import com.example.bookserver.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistic")
@CrossOrigin
public class StatisticController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserDetailsServiceImpl userService;



    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuthorStatistic> getBookReviews(){
        List<AuthorStatistic> statistics = new ArrayList<>();
        List<Author> authors = authorService.getAllAuthors();
        List<User> users = userService.getAllUsers();
        for (Author author : authors) {
            Double i = 0.0;
            List<Book> books = bookService.getBooksByAuthorId(author.getId());
            for (Book book : books) {
                i += new BookDTO(book).getAvgScore()*book.getReviews().size();
                for (User user : users) {
                    for (Book book1 : user.getReadBooks()) {
                        if (book1.getAuthor().getId() == author.getId())
                            i += 2.5;
                    }
                }

            }
            statistics.add(new AuthorStatistic(author.getName()+ " " + author.getSurname(), i));
        }
        return statistics;
    }


}
