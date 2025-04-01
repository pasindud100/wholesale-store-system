package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")  // ✅ Use Spring's component model
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer toEntity(CustomerDTO dto);
    CustomerDTO toDTO(Customer entity);
}
