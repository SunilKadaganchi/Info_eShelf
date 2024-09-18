package com.eShelf.info.e.library.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.UUID;

public class UserBookStatus extends BaseModel{
    private UUID userId;
    private UUID bookId;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

}
