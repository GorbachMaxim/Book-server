package com.example.bookserver.controller;


import com.example.bookserver.dto.MessageResponse;
import com.example.bookserver.dto.pojo.BookDTO;
import com.example.bookserver.model.Author;
import com.example.bookserver.model.Book;
import com.example.bookserver.model.Genre;
import com.example.bookserver.model.User;
import com.example.bookserver.repository.BookRepository;
import com.example.bookserver.service.AuthorService;
import com.example.bookserver.service.BookService;
import com.example.bookserver.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {



    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDTO> bookDTOS = new ArrayList<>();
        books.forEach(book->{
            bookDTOS.add(new BookDTO(book));
        });
        return bookDTOS;
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BookDTO getBook(@PathVariable long id){
        Book book = bookService.getBookById(id);
        return new BookDTO(book);
    }

    @GetMapping("/author/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<BookDTO> getBookByAuthor(@PathVariable long id){
        List<Book> books = bookService.getBooksByAuthorId(id);
        List<BookDTO> bookDTOS = new ArrayList<>();
        books.forEach(book->{
            bookDTOS.add(new BookDTO(book));
        });
        return bookDTOS;
    }


    @GetMapping("genre/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<BookDTO> getBookByGenre(@PathVariable long id){
        List<Book> books = bookService.getBooksByGenreId(id);
        List<BookDTO> bookDTOS = new ArrayList<>();
        books.forEach(book->{
            bookDTOS.add(new BookDTO(book));
        });
        return bookDTOS;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBook(@RequestBody @Valid Book book) {
        Author author = authorService.getAuthorById(book.getAuthor().getId());
        Genre genre = genreService.getGenreById(book.getGenre().getId());

        book.setAuthor(author);
        book.setGenre(genre);

        bookService.saveOrUpdate(book);
        return ResponseEntity.ok(new MessageResponse("Book CREATED"));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")//не работает на обзоры
    public ResponseEntity<?> updateBook(@RequestBody @Valid Book book){
        Book book1 = bookService.getBookById(book.getId());
        book1.setName(book.getName());
        book1.setISBN(book.getISBN());
        book1.setDescription(book.getDescription());
        book1.setImage(book.getImage());

        if(book.getAuthor() == null)
            book1.setAuthor(book.getAuthor());
        if(book.getGenre() == null)
            book1.setGenre(book.getGenre());

        Author author = authorService.getAuthorById(book.getAuthor().getId());
        Genre genre = genreService.getGenreById(book.getGenre().getId());

        if(author != null)
            book1.setAuthor(author);
        if(genre != null)
            book1.setGenre(genre);
        bookService.saveOrUpdate(book1);
        return ResponseEntity.ok(new MessageResponse("Book UPDATED"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable long id){
        bookService.deleteBookById(id);
        return ResponseEntity.ok(new MessageResponse("Book DELETED"));
    }


}
