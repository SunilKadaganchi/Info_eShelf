package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.BookAddRequestDto;
import com.eShelf.info.e.library.dto.BookUpdateRequestDto;
import com.eShelf.info.e.library.model.Book;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookServiceImpl implements BookService{
    @Override
    public boolean addBook(BookAddRequestDto bookAddRequestDto) {
        return false;
    }

    @Override
    public boolean updateBook(BookUpdateRequestDto bookUpdateRequestDto) {
        return false;
    }

    @Override
    public boolean deleteBook(UUID id) {
        return true;
    }

    @Override
    public Book getAllBooks() {
        return null;
    }
}
