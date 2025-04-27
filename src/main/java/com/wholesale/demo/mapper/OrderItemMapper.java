package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.OrderItemDTO;
import com.wholesale.demo.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "product.id", target = "productId")
    OrderItemDTO toDTO(OrderItem orderItem);

    @Mapping(source = "productId", target = "product.id")
    OrderItem toEntity(OrderItemDTO orderItemDTO);
}
