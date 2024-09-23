package com.eShelf.info.e.library.controller;

import com.eShelf.info.e.library.dto.BookAddRequestDto;
import com.eShelf.info.e.library.dto.BookUpdateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    @PostMapping("/add")
    public ResponseEntity<Boolean> addBook(@RequestBody BookAddRequestDto bookAddRequestDto){
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateBook(@RequestBody BookUpdateRequestDto bookUpdateRequestDto){
        return null;
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteBook(@PathVariable("id") UUID id){
        return null;
    }




}
