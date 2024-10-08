package com.eShelf.info.e.library.dto;

import com.eShelf.info.e.library.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookResponseDto {
    private String bookName;
    private UUID bookId;
    private String categoryName;
    private String authorName;
    private String description;
    private double rating;
    private int bookAvailableCount;
}
