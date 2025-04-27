package com.wholesale.demo.service;

import com.wholesale.demo.dto.CategoryDTO;
import com.wholesale.demo.exception.CategoryAlreadyExistsException;
import com.wholesale.demo.exception.CategoryNotFoundException;
import com.wholesale.demo.exception.ResourceNotFoundException;
import com.wholesale.demo.mapper.CategoryMapper;
import com.wholesale.demo.model.Category;
import com.wholesale.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private  CategoryRepository categoryRepository;  // Injecting the Category repository to interact with the database.

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        // Check if the category with the same name already exists
        if(categoryRepository.existsByName(categoryDTO.getName())) {
            throw new CategoryAlreadyExistsException("Category already exist with name "+categoryDTO.getName());
        }

        // Convert the categoryDto to a category entity
        Category categoryToSave = categoryMapper.toEntity(categoryDTO);

        // Save the category in the database
        Category savedCategory = categoryRepository.save(categoryToSave);

        // Convert and return the saved category entity as a CategoryDTO
        return categoryMapper.toDTO(savedCategory);
    }

    public Page<CategoryDTO> getAllCategories(int page, int size) {
        // Define pagination information
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);

        return categoriesPage.map(categoryMapper::toDTO);
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toDTO(category);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        // Find the existing category by its id
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        // Update the category name
        categoryToUpdate.setName(categoryDTO.getName());

        // Save the updated category and return it as a DTO
        Category updatedCategory = categoryRepository.save(categoryToUpdate);
        return categoryMapper.toDTO(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        // Check if the category exists before attempting to delete
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> searchCategory(String searchKeyword) {
        // Retrieve categories matching the search keyword
        List<Category> categories = categoryRepository.searchCategory(searchKeyword);

        // If no categories are found, throw an exception
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("No categories found matching with " + searchKeyword);
        }
        return categories.stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
