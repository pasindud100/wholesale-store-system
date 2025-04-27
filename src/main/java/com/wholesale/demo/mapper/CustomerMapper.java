package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

//componentModel = "spring" ensures that the generated implementation is managed by Spring as a Bean.
@Mapper(componentModel = "spring")
public interface CustomerMapper {

     Customer toEntity(CustomerDTO dto);
     CustomerDTO toDTO(Customer entity);
}
