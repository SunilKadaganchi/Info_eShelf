package com.eShelf.info.e.library.mapper;

import com.eShelf.info.e.library.dto.BookRequestDto;
import com.eShelf.info.e.library.model.Book;

public class BookDtoMapper {

    public static Book convertRequestDtoToEntity(BookRequestDto bookRequestDto){

        Book book = new Book();
        book.setBookName(bookRequestDto.getBookName());
        book.setAuthorName(bookRequestDto.getAuthorName());
        book.setDescription(bookRequestDto.getDescription());
        book.setBookAvailableCount(bookRequestDto.getBookAvailableCount());
        book.setRating(bookRequestDto.getRating());

        return book;

    }
}
