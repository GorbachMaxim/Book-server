package com.example.bookserver.dto;

import com.example.bookserver.dto.pojo.BookDTO;
import com.example.bookserver.model.Book;

public class ChatGptMessageResponse {
    private String message;

    private BookDTO bookDto;

    public ChatGptMessageResponse(String message, Book book) {
        this.message = message;
        this.bookDto = new BookDTO(book);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BookDTO getBookDto() {
        return bookDto;
    }

    public void setBookDto(BookDTO bookDto) {
        this.bookDto = bookDto;
    }
}
