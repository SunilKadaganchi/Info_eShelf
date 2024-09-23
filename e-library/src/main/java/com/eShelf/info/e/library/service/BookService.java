package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.BookAddRequestDto;
import com.eShelf.info.e.library.dto.BookUpdateRequestDto;
import com.eShelf.info.e.library.model.Book;

import java.util.UUID;

public interface BookService {
    boolean addBook(BookAddRequestDto bookAddRequestDto);
    boolean updateBook(BookUpdateRequestDto bookUpdateRequestDto);
    boolean deleteBook(UUID id);
    Book getAllBooks();
}
