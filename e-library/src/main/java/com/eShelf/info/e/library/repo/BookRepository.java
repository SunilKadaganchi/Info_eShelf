package com.eShelf.info.e.library.repo;

import com.eShelf.info.e.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
}
