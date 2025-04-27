package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.ProductDTO;
import com.wholesale.demo.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// The @Mapper annotation designates this interface as a MapStruct mapper, enabling the automatic generation
// of the implementation class at compile-time. The componentModel = "spring" ensures that the mapper is registered
// as a Spring bean, so it can be injected into other components.
@Mapper(componentModel = "spring")
public interface ProductMapper {

    // The @Mapping annotations define how to map fields from the Product entity to the ProductDTO.
    // Here, the "supplier.id" field from the Product entity is mapped to "supplierId" in the ProductDTO.
    // Similarly, "category.id" from the Product entity is mapped to "categoryId" in the ProductDTO.
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "category.id", target = "categoryId")
    // Converts a Product entity to a ProductDTO, mapping the entity's fields to the corresponding DTO fields.
    ProductDTO toDTO(Product product);

    // The @Mapping annotations specify how to map fields from the ProductDTO back to the Product entity.
    // "supplierId" from the ProductDTO is mapped to "supplier.id" in the Product entity,
    // and "categoryId" from the ProductDTO is mapped to "category.id" in the Product entity.
    @Mapping(source = "supplierId", target = "supplier.id")
    @Mapping(source = "categoryId", target = "category.id")
    // Converts a ProductDTO back to a Product entity, mapping the DTO's fields to the corresponding entity fields.
    Product toEntity(ProductDTO productDTO);
}
