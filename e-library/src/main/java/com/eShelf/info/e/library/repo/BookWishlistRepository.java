package com.eShelf.info.e.library.repo;

import com.eShelf.info.e.library.model.BookWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface BookWishlistRepository extends JpaRepository<BookWishlist, UUID> {

    @Transactional
    @Modifying
    @Query(value = "Insert into book_wishlist ( id, user_Id , book_Id , wishlist_Status, created_at, updated_at ) " +
            "values(UNHEX(REPLACE(UUID(),'-','')), ?1 , ?2 ,?3,NOW(), NOW() )" +
            "on duplicate key update  wishlist_Status = values(wishlist_Status), updated_at = NOW() " , nativeQuery = true)
    void addToWishlist( UUID userId , UUID bookId , boolean wishlistStatus );

}
