package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.OrderDTO;
import com.wholesale.demo.model.Orderss;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "customer.id", target = "customerId")
    OrderDTO toDTO(Orderss orderss);

    @Mapping(source = "customerId", target = "customer.id")
    Orderss toEntity(OrderDTO orderDTO);



}
