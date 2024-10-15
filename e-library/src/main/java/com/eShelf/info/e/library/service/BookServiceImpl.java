package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.BookRequestDto;
import com.eShelf.info.e.library.dto.BookResponseDto;
import com.eShelf.info.e.library.dto.CategoryResponseDto;
import com.eShelf.info.e.library.exception.IdNotFoundException;
import com.eShelf.info.e.library.mapper.BookDtoMapper;
import com.eShelf.info.e.library.model.*;
import com.eShelf.info.e.library.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleUnresolved;
import java.awt.image.ImageProducer;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookWishlistRepository bookWishlistRepository;
    @Autowired
    private UserBookRecordRepository userBookRecordRepository;

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
    public List<BookResponseDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookResponseDto> bookResponseDtos = new ArrayList<>();
        for(Book book : books){
            bookResponseDtos.add(BookDtoMapper.convertEntityToBookResponseDto(book));
        }
        return bookResponseDtos;
    }

    @Override
    public Map<String, List<BookResponseDto>> getTop5BooksCategoryWise() {
        List<Book> topRatedBooks = bookRepository.getTop5BooksCategoryWise();
        Map<String , List<BookResponseDto>> responseMap = new HashMap<>();

        for(Book book : topRatedBooks){
            Category category = book.getCategory();
            String categoryName = category.getCategoryName();

            BookResponseDto bookResponseDto = BookDtoMapper.convertEntityToBookResponseDto(book);

            if(responseMap.containsKey(categoryName)){
                List<BookResponseDto> bookResponseList =responseMap.get(categoryName);
                bookResponseList.add(bookResponseDto);
                responseMap.put(categoryName , bookResponseList);
            }
            else {
                List<BookResponseDto> list = new ArrayList<>();
                list.add(bookResponseDto);
                responseMap.put(categoryName,list);
            }
        }

        return responseMap;
    }

    @Override
    public Page<Book> getBooksPageWise(int pageNum, int pageSize, UUID categoryId) {

        Page<Book> booksInPage = bookRepository.findAllByCategory_id(PageRequest.of(pageNum,pageSize),categoryId);

        return booksInPage;

    }

    public BookResponseDto viewBook(UUID bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        return BookDtoMapper.convertEntityToBookResponseDto(book);
    }

    @Override
    public Boolean addToWishlist(Map<String, Object> reqBody) {
        bookWishlistRepository.addToWishlist( UUID.fromString( reqBody.get("userId").toString()) ,
                UUID.fromString( reqBody.get("bookId").toString()),Boolean.valueOf( reqBody.get("wishlistStatus").toString()) );
        return Boolean.valueOf( reqBody.get("wishlistStatus").toString());
    }

    //=================================================================================
    // implement multiThreading concept so no users can book same book at a time
    @Override
    public String reserveBook(Map<String, Object> reqBody) {
        UUID userId = UUID.fromString( reqBody.get("userId").toString());
        UUID bookId = UUID.fromString( reqBody.get("bookId").toString());

        Book book = bookRepository.findById(bookId).orElseThrow();

        if(book.getBookAvailableCount()<1){
            throw new RuntimeException("Books are not available with given ID");
        }

        book.setBookAvailableCount(book.getBookAvailableCount()-1);
        book = bookRepository.save(book);

        UserBookRecord reserveBook = new UserBookRecord(userId,bookId, BookStatus.RESERVED,false );
        userBookRecordRepository.save(reserveBook);

        return "Book reserved SuccessFully.. Collect it by evening 6:00 pm , else it will be released";
    }

    @Override
    public String collectBook(Map<String, Object> reqBody)   {
        UUID userId = UUID.fromString( reqBody.get("userId").toString());
        UUID bookId = UUID.fromString( reqBody.get("bookId").toString());
        UUID recordId = UUID.fromString( reqBody.get("id").toString());

        UserBookRecord record = userBookRecordRepository.findByIdAndUserIdAndBookId(recordId,userId,bookId).orElseThrow();

        if(record.getBookStatus().equals(BookStatus.RESERVED)){
            record.setBookStatus(BookStatus.COLLECTED);
            userBookRecordRepository.save(record);

        }
        else{
            throw new RuntimeException("Book is Not Reserved...");
        }
        // collected book or renewed book has to return with in 10 days
        // return time can be calculated using Updated_at field timing
        // adding 10 days to the updated field will give the return time.
        Instant collectedTime = record.getUpdatedAt();
        Instant returnTime = collectedTime.plus(Duration.ofDays(10));

        // implement collect timings and return timings
        return "Book Collected Successfully , Book has to renewed or returned with in 10 days :-"+returnTime;
    }

    @Override
    public void releaseBooks() {
        List<UserBookRecord> reservedBooks = userBookRecordRepository.getReservedBooks();

        for(UserBookRecord item : reservedBooks){

            Book book = bookRepository.findById(item.getBookId()).orElseThrow();
            book.setBookAvailableCount(book.getBookAvailableCount()+1);
            bookRepository.save(book);

            userBookRecordRepository.delete(item);
        }
    }


    @Override
    public String renewBook(Map<String, Object> reqBody) {
        UUID userId = UUID.fromString( reqBody.get("userId").toString());
        UUID bookId = UUID.fromString( reqBody.get("bookId").toString());
        UUID recordId = UUID.fromString( reqBody.get("id").toString());

        UserBookRecord record = userBookRecordRepository.findByIdAndUserIdAndBookId(recordId,userId,bookId).orElseThrow();

            if(record.getBookStatus().equals(BookStatus.COLLECTED) && !(record.isRenewed())){
                record.setRenewed(true);
                userBookRecordRepository.save(record);

                Instant renewedTime = record.getCreatedAt();
                Instant returnTime = renewedTime.plus(Duration.ofDays(20));

                // implement collect timings and return timings
                return "Book Renewed Successfully , Book has to be returned  :-"+returnTime;

            }
            else{
                return "Book can Renewed only ONCE , something went wrong..";
            }
    }

    @Override
    public String returnBook(Map<String, Object> reqBody) {

        UUID userId = UUID.fromString( reqBody.get("userId").toString());
        UUID bookId = UUID.fromString( reqBody.get("bookId").toString());
        UUID recordId = UUID.fromString( reqBody.get("id").toString());

        Book book = bookRepository.findById(bookId).orElseThrow();

        UserBookRecord record = userBookRecordRepository.findByIdAndUserIdAndBookId(recordId,userId,bookId).orElseThrow();

        if(record.getBookStatus().equals(BookStatus.COLLECTED)){
            book.setBookAvailableCount(book.getBookAvailableCount()+1);
            bookRepository.save(book);
            record.setBookStatus(BookStatus.RETURNED);
            userBookRecordRepository.save(record);
        }
        else{
            throw new RuntimeException("Something went wrong.....");
        }


        return "Book returned Successfully.Rate the Book, Thank you...";
    }











    ////////////////////////////////////////////////
    @Override
    public List<BookResponseDto> getBooksByCategoryId(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()){
            throw new IdNotFoundException("Given Category ID is Not Found");
        }

        List<Book> bookList = bookRepository.findAllByCategory_id(category.get().getId());
        if(bookList.isEmpty()){
            return new ArrayList<>();
        }
        List<BookResponseDto> bookResponseDtos = new ArrayList<>();
        for(Book book : bookList){
            bookResponseDtos.add(BookDtoMapper.convertEntityToBookResponseDto(book));
        }
        return bookResponseDtos;
    }

    @Override
    public List<BookResponseDto> getBooksByCategoryName(String name) {
        CategoryResponseDto category = categoryService.getCategoryByName(name);

        UUID categoryId = category.getId();
        return getBooksByCategoryId(categoryId);
    }








    //    @Override
//    public Map<String, List<BookResponseDto>> getBooksCategoryWise() {
//       Map<String,List<Book>> responseMap = new HashMap<>();
//
//       List<Book> bookList = bookRepository.findAll();
//       List<BookResponseDto> bookResponseDtos = new ArrayList<>();
//
//       for (Book book : bookList){
//            Category category = book.getCategory();
//            String categoryName = category.getCategoryName();
//
//            if(responseMap.containsKey(categoryName)){
//                responseMap.get(categoryName).add(book);
//            }
//            else{
//                List<Book> list = new ArrayList<>();
//                list.add(book);
//                responseMap.put(categoryName,list);
//            }
//       }
//
//       return null;
//    }
}
