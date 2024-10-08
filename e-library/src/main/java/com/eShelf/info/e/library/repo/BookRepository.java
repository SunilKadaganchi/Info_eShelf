package com.eShelf.info.e.library.repo;

import com.eShelf.info.e.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

     List<Book> findAllByCategory_id(UUID categoryId);

     @Query(value = " select tempTable.id , tempTable.created_at , tempTable.updated_at , tempTable.author_name , tempTable.book_available_count , tempTable.book_name , tempTable.description, tempTable.rating , tempTable.category_id from\n" +
             "(\n" +
             "select * ,\n" +
             "row_number() over(Partition by category_id order by rating desc)  as rowNum\n" +
             "from info_library.book   ) tempTable\n" +
             "where rowNum <= 5 ;\n  ", nativeQuery = true)
     List<Book> getTop5BooksCategoryWise();

     Page<Book> findAllByCategory_id(Pageable pageable , UUID categoryId);


}
