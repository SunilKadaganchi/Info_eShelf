package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.BookRequestDto;
import com.eShelf.info.e.library.dto.BookResponseDto;
import com.eShelf.info.e.library.dto.CategoryResponseDto;
import com.eShelf.info.e.library.exception.IdNotFoundException;
import com.eShelf.info.e.library.mapper.BookDtoMapper;
import com.eShelf.info.e.library.model.Book;
import com.eShelf.info.e.library.model.BookStatus;
import com.eShelf.info.e.library.model.Category;
import com.eShelf.info.e.library.model.UserBookStatus;
import com.eShelf.info.e.library.repo.BookRepository;
import com.eShelf.info.e.library.repo.BookWishlistRepository;
import com.eShelf.info.e.library.repo.CategoryRepository;
import com.eShelf.info.e.library.repo.UserBookStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleUnresolved;
import java.awt.image.ImageProducer;
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
    private UserBookStatusRepository userBookStatusRepository;

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

        UserBookStatus reserveBook = new UserBookStatus(userId,bookId, BookStatus.RESERVED);
        userBookStatusRepository.save(reserveBook);

        return "Book reserved SuccessFully.. Collect it by evening 6:00 pm , else it will be released";
    }

    @Override
    public String collectBook(Map<String, Object> reqBody) {
        UUID userId = UUID.fromString( reqBody.get("userId").toString());
        UUID bookId = UUID.fromString( reqBody.get("bookId").toString());

        UserBookStatus userBookStatus = userBookStatusRepository.findByUserIdAndBookId(userId,bookId).orElseThrow();

        if(userBookStatus.getBookStatus().equals(BookStatus.RESERVED)){
            userBookStatus.setBookStatus(BookStatus.COLLECTED);
            userBookStatusRepository.save(userBookStatus);
        }

        // implement collect timings and return timings
        return "Book Collected Successfully..";
    }

    @Override
    public void releaseBooks() {
        List<UserBookStatus> reservedBooks = userBookStatusRepository.getReservedBooks();

        List<UserBookStatus> all = userBookStatusRepository.findAll();

        for(UserBookStatus item : reservedBooks){

            Book book = bookRepository.findById(item.getBookId()).orElseThrow();
            book.setBookAvailableCount(book.getBookAvailableCount()+1);
            bookRepository.save(book);

            userBookStatusRepository.delete(item);
        }
        return ;
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
