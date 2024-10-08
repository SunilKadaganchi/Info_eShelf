package com.eShelf.info.e.library.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table( uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "bookId"})
})
public class BookWishlist extends BaseModel{
    @Column(name="user_Id" , nullable = false)
    private UUID userId;
    @Column(name="book_Id",  nullable = false)
    private UUID bookId;
    @Column(name = "wishlist_Status",nullable = false)
    private boolean wishlistStatus;

}

