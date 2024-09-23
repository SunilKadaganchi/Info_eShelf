package com.eShelf.info.e.library.controller;

import com.eShelf.info.e.library.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") UUID id){
        return null;
    }

    @PostMapping()
    public void addCategory(@RequestBody String categoryName){
        return ;
    }

    @PostMapping("/update")
    public void updateCategory(@RequestBody String CategoryName){
        return;
    }

    @DeleteMapping("/delete")
    public  ResponseEntity<Boolean> deleteCategory(@PathVariable("id") UUID id){
        return ResponseEntity.ok(true);
    }


}
