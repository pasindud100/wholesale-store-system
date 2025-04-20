package com.wholesale.demo.controller;

import com.wholesale.demo.dto.CategoryDTO;
import com.wholesale.demo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * CategoryController is a REST controller that handles HTTP requests related to category management.
 * It provides endpoints for creating, retrieving, updating, and deleting categories.
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Constructor for CategoryController
     * @param categoryService the service that handles business logic for categories
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    /**
     * Endpoint to save a new category.
     * @param categoryDTO the data transfer object containing category details
     * @return ResponseEntity containing the saved CategoryDTO and HTTP status 201 (Created)
     */
    @PostMapping("/save")
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
    /**
     * Endpoint to retrieve all categories.
     * @return ResponseEntity containing a list of CategoryDTOs and HTTP status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    /**
     * Endpoint to retrieve a category by its ID.
     * @param id the ID of the category to retrieve
     * @return ResponseEntity containing the CategoryDTO and HTTP status 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
    /**
     * Endpoint to update an existing category.
     * @param id the ID of the category to update
     * @param categoryDTO the data transfer object containing updated category details
     * @return ResponseEntity containing the updated CategoryDTO and HTTP status 200 (OK)
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }
    /**
     * Endpoint to delete a category by its ID
     * @param id the ID of the category to delete
     * @return ResponseEntity containing a success message and HTTP status 200 (OK)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category is deleted successfully");
    }
}