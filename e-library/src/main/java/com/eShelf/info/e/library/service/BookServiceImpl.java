package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.BookRequestDto;
import com.eShelf.info.e.library.mapper.BookDtoMapper;
import com.eShelf.info.e.library.model.Book;
import com.eShelf.info.e.library.model.Category;
import com.eShelf.info.e.library.repo.BookRepository;
import com.eShelf.info.e.library.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book addBook(BookRequestDto bookRequestDto, String categoryName) {
        Book book = BookDtoMapper.convertRequestDtoToEntity(bookRequestDto);

        Optional<Category> categoryOptional = categoryRepository.findByCategoryName(categoryName);
        Category category;
        if(categoryOptional.isEmpty()){
            category = new Category();
            category.setCategoryName(categoryName);
            category = categoryRepository.save(category);
        }
        else{
            category = categoryOptional.get();
        }

        book.setCategory(category);

        return bookRepository.save(book);

    }

    @Override
    public Book updateBook(UUID id, BookRequestDto bookRequestDto) {
        Optional<Book> savedBook = bookRepository.findById(id);
        if(savedBook.isEmpty()){
            throw new RuntimeException("Book with given id is not present");
        }

        Book updatedBook = savedBook.get();
        updatedBook.setBookName(bookRequestDto.getBookName());
        updatedBook.setBookAvailableCount(bookRequestDto.getBookAvailableCount());
        updatedBook.setRating(bookRequestDto.getRating());
        updatedBook.setAuthorName(bookRequestDto.getAuthorName());
        updatedBook.setDescription(bookRequestDto.getDescription());

        updatedBook = bookRepository.save(updatedBook);
        return updatedBook;

    }

    @Override
    public boolean deleteBook(UUID id) {
        Optional<Book> savedBook = bookRepository.findById(id);
        savedBook.ifPresent(book -> bookRepository.delete(book));
        return true;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
