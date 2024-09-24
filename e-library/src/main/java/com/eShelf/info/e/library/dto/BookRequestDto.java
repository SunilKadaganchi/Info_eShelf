package com.eShelf.info.e.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDto {
    private String bookName;
    private String categoryName;
    private String authorName;
    private String description;
    private double rating;
    private int bookAvailableCount;
}
