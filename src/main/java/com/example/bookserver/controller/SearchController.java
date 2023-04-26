package com.example.bookserver.controller;


import com.example.bookserver.dto.pojo.BookDTO;
import com.example.bookserver.model.Book;
import com.example.bookserver.model.Review;
import com.example.bookserver.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/search")
@CrossOrigin
public class SearchController {


    @Autowired
    BookService bookService;

    @GetMapping("/{search}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Set<BookDTO> search(@PathVariable String search){
        List<Book> books = bookService.getAllBooks();
        books.sort(Comparator.comparing(Book::getName));


        List<BookDTO> bookDTOS = new ArrayList<>();
        books.forEach(book->{
            bookDTOS.add(new BookDTO(book));
        });
        Set<BookDTO> searchedBooks = new HashSet<>();

        bookDTOS.forEach(bookDTO -> {
            if(performKMPSearch(bookDTO.getName(), search).size()>0)
                searchedBooks.add(bookDTO);
            else if(performKMPSearch(bookDTO.getDescription(), search).size()>0)
                searchedBooks.add(bookDTO);
            else if(performKMPSearch(bookDTO.getAuthor().getName(), search).size()>0)
                searchedBooks.add(bookDTO);
            else if(performKMPSearch(bookDTO.getGenre().getName(), search).size()>0)
                searchedBooks.add(bookDTO);
        });

        bookDTOS.forEach(bookDTO -> {
            if(performKMPSearch(bookDTO.getAuthor().getBiography(), search).size()>0)
                searchedBooks.add(bookDTO);
            else if(performKMPSearch(bookDTO.getGenre().getDescription(), search).size()>0)
                searchedBooks.add(bookDTO);

            bookDTO.getReviews().forEach(reviewDTO -> {
                if(performKMPSearch(reviewDTO.getText(), search).size()>0)
                    searchedBooks.add(bookDTO);
                else if(performKMPSearch(reviewDTO.getUser().getUsername(), search).size()>0)
                    searchedBooks.add(bookDTO);
            });
        });
        return searchedBooks;
    }


    private static int[] compilePatternArray(String pattern) {
        int patternLength = pattern.length();
        int len = 0;
        int i = 1;
        int[] compliedPatternArray = new int[patternLength];
        compliedPatternArray[0] = 0;

        while (i < patternLength) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                compliedPatternArray[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = compliedPatternArray[len - 1];
                } else {
                    compliedPatternArray[i] = len;
                    i++;
                }
            }
        }
        //System.out.println("Compiled Pattern Array " + Arrays.toString(compliedPatternArray));
        return compliedPatternArray;
    }


    private static List<Integer> performKMPSearch(String text, String pattern) {
        int[] compliedPatternArray = compilePatternArray(pattern);

        int textIndex = 0;
        int patternIndex = 0;

        List<Integer> foundIndexes = new ArrayList<>();

        while (textIndex < text.length()) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }
            if (patternIndex == pattern.length()) {
                foundIndexes.add(textIndex - patternIndex);
                patternIndex = compliedPatternArray[patternIndex - 1];
            }

            else if (textIndex < text.length() && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                if (patternIndex != 0)
                    patternIndex = compliedPatternArray[patternIndex - 1];
                else
                    textIndex = textIndex + 1;
            }
        }
        return foundIndexes;
    }

}
