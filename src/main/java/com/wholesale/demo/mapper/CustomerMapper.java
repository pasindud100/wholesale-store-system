package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // to convert customerDto to customer entity
    Customer toEntity(CustomerDTO dto);
    CustomerDTO toDTO(Customer entity);
}
