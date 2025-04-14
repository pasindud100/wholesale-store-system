package com.wholesale.demo.mapper;

import com.wholesale.demo.dto.InvoiceDTO;
import com.wholesale.demo.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    @Mapping(source = "order.id", target = "orderId")
    InvoiceDTO toDTO(Invoice invoice);

    @Mapping(source = "orderId", target = "order.id")
    Invoice toEntity(InvoiceDTO invoiceDTO);
}