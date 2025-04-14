package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.OrderDTO;
import com.wholesale.demo.model.Orderss;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(source = "payment.id", target = "paymentId")
    OrderDTO toDTO(Orderss orderss);

    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(source = "invoiceId", target = "invoice.id")
    @Mapping(source = "paymentId", target = "payment.id")
    Orderss toEntity(OrderDTO orderssDTO);
}