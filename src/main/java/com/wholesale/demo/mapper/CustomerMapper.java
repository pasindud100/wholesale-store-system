package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class); //create instance

    Customer toEntity(CustomerDTO dto); //This converts customerDto to customer entity
    CustomerDTO toDTO(Customer entity);
}
