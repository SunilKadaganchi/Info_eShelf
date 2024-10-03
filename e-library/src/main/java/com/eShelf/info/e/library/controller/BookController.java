package com.eShelf.info.e.library.controller;

import com.eShelf.info.e.library.dto.BookRequestDto;
import com.eShelf.info.e.library.dto.BookResponseDto;
import com.eShelf.info.e.library.model.Book;
import com.eShelf.info.e.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping()
    public ResponseEntity<List<BookResponseDto>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping("/add")
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


    @GetMapping("/categoryid/{id}")
    public ResponseEntity<List<BookResponseDto>> getBooksByCategoryId(@PathVariable("id") UUID id){
        return ResponseEntity.ok(bookService.getBooksByCategoryId(id));
    }

    @GetMapping("/categoryname/{name}")
    public  ResponseEntity<List<BookResponseDto>> getBooksByCategoryName(@PathVariable("name") String name){
        return ResponseEntity.ok(bookService.getBooksByCategoryName(name));
    }

//    @GetMapping("/category")
//    // string in map represents the category name
//    public ResponseEntity<Map<String,List<BookResponseDto>>> getBooksCategoryWise(){
//        return ResponseEntity.ok(bookService.getBooksCategoryWise());
//    }


    //this method will return the list of top 5 rated books under each category to display on the Home screen
     @GetMapping("/dashboard")
    public ResponseEntity<Map<String, List<BookResponseDto>>> getBooksOnDashboard(){
        return ResponseEntity.ok(bookService.getTop5BooksCategoryWise());
     }


     @GetMapping("/paginated/{id}")
    public ResponseEntity<Page<Book>> getBooksInPages( @RequestParam(defaultValue = "0") int pageNo , @RequestParam(defaultValue = "5") int pageSize , @PathVariable(value = "id") UUID categoryId)
     {
         return ResponseEntity.ok( bookService.getBooksPageWise(pageNo,pageSize,categoryId));
     }
}
