package com.example.bookserver.mapper;

import com.example.bookserver.dto.pojo.BookDTO;
import com.example.bookserver.model.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    Book toDTO(BookDTO dto);
    BookDTO toEntity(Book model);
}
