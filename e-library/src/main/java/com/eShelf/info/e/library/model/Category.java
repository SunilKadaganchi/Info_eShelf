package com.eShelf.info.e.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name  = "Book_Category")
@Getter
@Setter
public class Category extends BaseModel{
    private String categoryName;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
