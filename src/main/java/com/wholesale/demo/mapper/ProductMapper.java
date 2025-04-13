package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.ProductDTO;
import com.wholesale.demo.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "category.id", target = "categoryId")
    ProductDTO toDTO(Product product);

    @Mapping(source = "supplierId", target = "supplier.id")
    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductDTO productDTO);
}