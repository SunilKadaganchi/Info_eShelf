package com.eShelf.info.e.library.repo;

import com.eShelf.info.e.library.model.BookStatus;
import com.eShelf.info.e.library.model.UserBookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserBookStatusRepository extends JpaRepository<UserBookStatus, UUID> {

    Optional<UserBookStatus> findByUserIdAndBookId(UUID userId, UUID bookId);

    @Query(value = " select * from user_book_status where book_status='RESERVED' ", nativeQuery = true)
    List<UserBookStatus> getReservedBooks();
}

