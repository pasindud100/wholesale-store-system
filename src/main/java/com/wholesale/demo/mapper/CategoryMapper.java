package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.CategoryDTO;
import com.wholesale.demo.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDTO dto);
    CategoryDTO toDTO(Category entity);
}
