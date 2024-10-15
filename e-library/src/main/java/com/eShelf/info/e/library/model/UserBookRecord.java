package com.eShelf.info.e.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Entity
@Getter
@Setter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId","bookId","id"}))
public class UserBookRecord extends BaseModel{

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID bookId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    private boolean isRenewed;


    public UserBookRecord() {
    }

    public UserBookRecord(UUID userId, UUID bookId, BookStatus bookStatus, boolean isRenewed) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookStatus = bookStatus;
        this.isRenewed = isRenewed;
    }
}
