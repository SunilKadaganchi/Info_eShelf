package com.eShelf.info.e.library.controller;

import com.eShelf.info.e.library.dto.BookRequestDto;
import com.eShelf.info.e.library.model.Book;
import com.eShelf.info.e.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping()
    public ResponseEntity<Book> addBook(@RequestBody BookRequestDto bookRequestDto){
        return ResponseEntity.ok(bookService.addBook(bookRequestDto, bookRequestDto.getCategoryName()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") UUID id, @RequestBody BookRequestDto bookRequestDto){
        return ResponseEntity.ok(bookService.updateBook(id,bookRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }




}
