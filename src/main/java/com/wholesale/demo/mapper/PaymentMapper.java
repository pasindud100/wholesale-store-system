package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.PaymentDTO;
import com.wholesale.demo.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "invoice.id", target = "invoiceId")
    PaymentDTO toDTO(Payment payment);

    @Mapping(source = "orderId", target = "order.id")
    @Mapping(source = "invoiceId", target = "invoice.id")
    Payment toEntity(PaymentDTO paymentDTO);
}