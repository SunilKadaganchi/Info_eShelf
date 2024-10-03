package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.BookRequestDto;
import com.eShelf.info.e.library.dto.BookResponseDto;
import com.eShelf.info.e.library.model.Book;
import com.eShelf.info.e.library.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BookService {
    Book addBook(BookRequestDto bookRequestDto, String categoryName);
    Book updateBook(UUID id, BookRequestDto bookRequestDto);
    boolean deleteBook(UUID id);
    List<BookResponseDto> getAllBooks();

    List<BookResponseDto> getBooksByCategoryId(UUID id);

    List<BookResponseDto> getBooksByCategoryName(String name);

    Map<String, List<BookResponseDto>> getTop5BooksCategoryWise();

    Page<Book> getBooksPageWise(int pageNum, int pageSize,UUID categoryId);
}
