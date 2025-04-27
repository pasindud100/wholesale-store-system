package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.CategoryDTO;
import com.wholesale.demo.model.Category;
import org.mapstruct.Mapper;

//@Mapper annotation tells mapstruct to generate an implementation of this interface at compile time.
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    //  maps a CategoryDTO to a Category entity.
    Category toEntity(CategoryDTO dto);
    CategoryDTO toDTO(Category entity);
}
