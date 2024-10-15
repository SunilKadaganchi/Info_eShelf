package com.eShelf.info.e.library.repo;

import com.eShelf.info.e.library.model.UserBookRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserBookRecordRepository extends JpaRepository<UserBookRecord, UUID> {

    Optional<UserBookRecord> findByIdAndUserIdAndBookId(UUID id ,UUID userId, UUID bookId);

    @Query(value = " select * from User_Book_Record where book_status='RESERVED' ", nativeQuery = true)
    List<UserBookRecord> getReservedBooks();
}
