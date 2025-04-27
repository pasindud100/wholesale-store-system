package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.SupplierDTO;
import com.wholesale.demo.model.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toEntity(SupplierDTO dto);
    SupplierDTO toDTO(Supplier entity);
}
