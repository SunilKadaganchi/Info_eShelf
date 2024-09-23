package com.eShelf.info.e.library.controller;

import com.eShelf.info.e.library.dto.CategoryRequestDto;
import com.eShelf.info.e.library.dto.CategoryResponseDto;
import com.eShelf.info.e.library.model.Category;
import com.eShelf.info.e.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<String>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping()
    public ResponseEntity<CategoryResponseDto> addCategory(@RequestBody CategoryRequestDto categoryRequestDto){
        return ResponseEntity.ok(categoryService.addCategory(categoryRequestDto.getName()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable("id") UUID id,@RequestBody CategoryRequestDto categoryRequestDto){

        return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequestDto.getName()));
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<Boolean> deleteCategory(@PathVariable("id") UUID id){
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }


}
