package com.example.bookserver.service;


import com.example.bookserver.model.Book;
import com.example.bookserver.model.Review;
import com.example.bookserver.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book getBookById(long id){
        return bookRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public void saveOrUpdate(Book book){
        bookRepository.save(book);
    }

    public void deleteBookById(long id){
        bookRepository.deleteById(id);
    }

    public void addReview(Review review, long id){
        Book book = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        bookRepository.save(book);
    }
}
