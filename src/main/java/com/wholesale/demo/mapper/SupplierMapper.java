package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.SupplierDTO;
import com.wholesale.demo.model.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    Supplier toSupplier(SupplierDTO supplierDTO);
    SupplierDTO toSupplierDTO(Supplier supplier);
}
