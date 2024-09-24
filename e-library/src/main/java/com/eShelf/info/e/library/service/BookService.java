package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.BookRequestDto;
import com.eShelf.info.e.library.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {
    Book addBook(BookRequestDto bookRequestDto, String categoryName);
    Book updateBook(UUID id, BookRequestDto bookRequestDto);
    boolean deleteBook(UUID id);
    List<Book> getAllBooks();
}
