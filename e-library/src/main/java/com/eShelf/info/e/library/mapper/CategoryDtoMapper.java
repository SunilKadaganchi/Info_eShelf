package com.eShelf.info.e.library.mapper;

import com.eShelf.info.e.library.dto.CategoryResponseDto;
import com.eShelf.info.e.library.model.Category;

public class CategoryDtoMapper {
    public static CategoryResponseDto convertCategoryToResponseDto(Category category){
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setCategoryName(category.getCategoryName());
        responseDto.setId(category.getId());

          return responseDto;
    }
}
