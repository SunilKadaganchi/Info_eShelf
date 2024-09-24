package com.eShelf.info.e.library.service;

import com.eShelf.info.e.library.dto.CategoryResponseDto;
import com.eShelf.info.e.library.mapper.CategoryDtoMapper;
import com.eShelf.info.e.library.model.Category;
import com.eShelf.info.e.library.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<String> getAllCategories() {
        List<String> categoriesList = new ArrayList<>();
        for(Category item : categoryRepository.findAll()){
            categoriesList.add(item.getCategoryName());
        }
        return categoriesList;
    }

    @Override
    public CategoryResponseDto getCategoryByName(String name) {
        Category category = categoryRepository.findByCategoryName(name).get();
        return CategoryDtoMapper.convertCategoryToResponseDto(category);
    }

    @Override
    public CategoryResponseDto addCategory(String categoryName) {
        Category savedCategory = new Category();
        savedCategory.setCategoryName(categoryName);
        savedCategory = categoryRepository.save(savedCategory);
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(savedCategory.getId());
        responseDto.setCategoryName(savedCategory.getCategoryName());
        return responseDto;
    }

    @Override
    public CategoryResponseDto updateCategory(UUID categoryId, String newCategoryName) {
        Category category = categoryRepository.findById(categoryId).get();
        category.setCategoryName(newCategoryName);
         Category updatedCategory = categoryRepository.save(category);
         return CategoryDtoMapper.convertCategoryToResponseDto(updatedCategory);

    }

    @Override
    public boolean deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        if(category != null){
            categoryRepository.delete(category);
        }
        return true;
    }


}
