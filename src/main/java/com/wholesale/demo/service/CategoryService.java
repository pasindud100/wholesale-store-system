package com.wholesale.demo.service;

import com.wholesale.demo.dto.CategoryDTO;
import com.wholesale.demo.mapper.CategoryMapper;
import com.wholesale.demo.model.Category;
import com.wholesale.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(categoryMapper::toDTO);
    }
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            Category categoryToUpdate = categoryMapper.toEntity(categoryDTO);
            categoryToUpdate.setId(id);
            Category updatedCategory = categoryRepository.save(categoryToUpdate);
            return categoryMapper.toDTO(updatedCategory);
        }
        return null;
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}