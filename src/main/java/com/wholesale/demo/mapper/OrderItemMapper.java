package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.OrderItemDTO;
import com.wholesale.demo.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "order.id", target = "orderId")
    OrderItemDTO toDTO(OrderItem orderItem);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "orderId ", target = "order.id")
    OrderItem toEntity(OrderItemDTO orderItemDTO);
}