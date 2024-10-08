package com.eShelf.info.e.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId","bookId"}))
public class UserBookStatus extends BaseModel {
    @Column(nullable = false)
    private UUID userId;
    @Column(nullable = false)
    private UUID bookId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    public UserBookStatus() {
    }

    public UserBookStatus(UUID userId, UUID bookId, BookStatus bookStatus) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookStatus = bookStatus;
    }
}
