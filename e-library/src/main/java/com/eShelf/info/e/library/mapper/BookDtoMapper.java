package com.eShelf.info.e.library.mapper;

import com.eShelf.info.e.library.dto.BookRequestDto;
import com.eShelf.info.e.library.dto.BookResponseDto;
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

    public static BookResponseDto convertEntityToBookResponseDto(Book book){
        BookResponseDto responseDto = new BookResponseDto();
        responseDto.setBookName(book.getBookName());
        responseDto.setBookId(book.getId());
        responseDto.setCategoryName(book.getCategory().getCategoryName());
        responseDto.setDescription(book.getDescription());
        responseDto.setAuthorName(book.getAuthorName());
        responseDto.setRating(book.getRating());
        responseDto.setBookAvailableCount(book.getBookAvailableCount());

        return responseDto;
    }
}
