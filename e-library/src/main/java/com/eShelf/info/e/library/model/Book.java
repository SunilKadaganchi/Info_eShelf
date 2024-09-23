package com.eShelf.info.e.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Book extends BaseModel{
    private String bookName;
    @ManyToOne
    private Category category;
    private String authorName;
    private String description;
    private double rating;
    private int bookAvailableCount;

    public Book() {
    }

    public Book(String bookName, Category category, String authorName, String description, double rating, int bookAvailableCount) {
        this.bookName = bookName;
        this.category = category;
        this.authorName = authorName;
        this.description = description;
        this.rating = rating;
        this.bookAvailableCount = bookAvailableCount;
    }
}
