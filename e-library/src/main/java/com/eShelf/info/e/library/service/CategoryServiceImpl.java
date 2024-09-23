package com.eShelf.info.e.library.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Override
    public String addCategory(String categoryName) {
        return null;
    }

    @Override
    public String updateCategory(UUID categoryId, String newCategoryName) {
        return null;
    }

    @Override
    public boolean deleteCategory(UUID categoryId) {
        return false;
    }

    @Override
    public UUID getCategoryByName(String name) {
        return null;
    }
}
