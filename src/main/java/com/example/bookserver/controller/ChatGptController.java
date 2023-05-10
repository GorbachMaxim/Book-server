package com.example.bookserver.controller;

import com.example.bookserver.dto.ChatGptMessageResponse;
import com.example.bookserver.model.Book;
import com.example.bookserver.service.BookService;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/chatGPT")
@CrossOrigin
public class ChatGptController {
    @Autowired
    private ChatgptService chatgptService;

    @Autowired
    private BookService bookService;

    private static<T> T getRandomElement(List<T> list) {
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> chatGPT(){
        List<Book> books = bookService.getAllBooks();

        Book book = getRandomElement(books);

        System.err.println(book.getName());
        String responseMessage = chatgptService.sendMessage("Красочно порекомендуй книгу " + book.getName());
        responseMessage = responseMessage.replaceAll("\n|\r\n", "");
        responseMessage += chatgptService.sendMessage("Продолжи сообщение: " + responseMessage);


        int i = responseMessage.lastIndexOf('.');
        responseMessage = responseMessage.substring(0, i+1);
        return ResponseEntity.ok(new ChatGptMessageResponse(responseMessage, book));
    }


}
