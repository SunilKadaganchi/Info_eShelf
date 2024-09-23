package com.eShelf.info.e.library.service;

import java.util.UUID;

public interface CategoryService {
    String addCategory(String categoryName);
    String updateCategory(UUID categoryId , String newCategoryName);
    boolean deleteCategory(UUID categoryId);
    UUID getCategoryByName(String name);
}
