package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.CategoryResponseDto;
import com.eShelf.info.e.library.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<String> getAllCategories();
    CategoryResponseDto getCategoryByName(String name);
    CategoryResponseDto addCategory(String categoryName);
    CategoryResponseDto updateCategory(UUID categoryId, String newCategoryName);
    boolean deleteCategory(UUID categoryId);

}
