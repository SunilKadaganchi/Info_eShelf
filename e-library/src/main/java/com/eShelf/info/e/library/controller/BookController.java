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

    // this API will return the list of top 5 rated books under each category to display on the Home screen
    // this API will be called, when user opens the Dashboard of application
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, List<BookResponseDto>>> getBooksOnDashboard(){
        return ResponseEntity.ok(bookService.getTop5BooksCategoryWise());
    }

    // this API will return the all books under particular category in Paginated manner,
    // this API will be called when user clicked on category name to view all the books under that category
    @GetMapping("/paginated/{id}")
    public ResponseEntity<Page<Book>> getBooksInPages( @RequestParam(defaultValue = "0") int pageNo , @RequestParam(defaultValue = "5") int pageSize , @PathVariable(value = "id") UUID categoryId)
    {
        return ResponseEntity.ok( bookService.getBooksPageWise(pageNo,pageSize,categoryId));
    }

    // this API will return book with the given ID
    // this API gets called once user clicks on view book or more details of any particular book.
    @GetMapping("/view/{id}")
    public ResponseEntity<BookResponseDto> viewBook(@PathVariable(value = "id") UUID bookId){
        return ResponseEntity.ok( bookService.viewBook(bookId));
    }

    // this API is used by the USER to add the book into their wishlist
    // wishlist option is provided , once we viewed the more details of a book.
    //this API is used add & remove the book from wishlist.
    @PostMapping("/wishlist")
    public ResponseEntity<Boolean> addToWishlist(@RequestBody Map<String,Object> reqBody){
        boolean response = bookService.addToWishlist(reqBody);
       return ResponseEntity.ok(response);
    }

    //Using this API, we can reserve the book , if the book_available_count is more than 0 in the library
    // this API is called , when user clicks on reserve option of particular book
    @PostMapping("/reserve")
    public ResponseEntity<String> reserveBook(@RequestBody Map<String , Object> reqBody){
        return ResponseEntity.ok(bookService.reserveBook(reqBody));
    }

    //this API , changes the book state from RESERVED TO COLLECTED,
    // when user collects their already reserved book
    // this API can only be called by LIBRARY_ADMIN once the user collected the book from library
    @PostMapping("/collect")
    public ResponseEntity<String> collectFromLibrary(@RequestBody Map<String,Object> reqBody){
        return ResponseEntity.ok( bookService.collectBook(reqBody));
    }

    @GetMapping("/release")
    public ResponseEntity<Void> releaseReservedBooks(){
        bookService.releaseBooks();
        return ResponseEntity.ok().build();
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



}
