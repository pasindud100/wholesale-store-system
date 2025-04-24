package com.wholesale.demo.service;

import com.wholesale.demo.dto.InvoiceDTO;
import com.wholesale.demo.exception.*;
import com.wholesale.demo.mapper.InvoiceMapper;
import com.wholesale.demo.model.Invoice;
import com.wholesale.demo.model.OrderItem;
import com.wholesale.demo.model.Orderss;
import com.wholesale.demo.repository.InvoiceRepository;
import com.wholesale.demo.repository.OrderItemRepository;
import com.wholesale.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public InvoiceDTO createInvoice(InvoiceDTO invoiceDTO) throws OrderNotFoundException {
        Optional<Invoice> existingInvoice = invoiceRepository.findByOrderId(invoiceDTO.getOrderId());

        if (existingInvoice.isPresent()) {
            throw new ResourceNotFoundException("Invoice already exists for order ID: " + invoiceDTO.getOrderId());
        }

        Orderss existingOrder = orderRepository.findById(invoiceDTO.getOrderId()).
                orElseThrow(()-> new OrderNotFoundException("No any order with id "+
                        invoiceDTO.getOrderId() + " " + "For create an invoice."));

        Optional<OrderItem> orderHasOrderItems = Optional.ofNullable(orderItemRepository.
                findByOrderId(invoiceDTO.getOrderId()).
                orElseThrow(() -> new OrderItemNotFoundException("No any order items in order id \"" +
                        invoiceDTO.getOrderId() +"\". First add items to order before issue an invoice.")));

        double orderTotalAmount = existingOrder.getAmount();

        double amountDifference = orderTotalAmount - invoiceDTO.getAmount();
        if (amountDifference > 0) {
            throw new AmountGreateOrLessException(
                    "Order total amount is greater than invoice amount by LKR " + amountDifference +
                            ". Add the remaining amount to issue the invoice.");
        } else if (amountDifference < 0) {
            throw new AmountGreateOrLessException(
                    "Order total amount is less than invoice amount by LKR " + Math.abs(amountDifference) +
                            ". Get the remaining amount to issue the invoice.");
        }

        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDTO(invoice);
    }

    @Transactional(readOnly = true)
    public Page<InvoiceDTO> getAllInvoices(int page, int size) {
       Pageable pageable = PageRequest.of(page, size);
       Page<Invoice> invoicesPage = invoiceRepository.findAll(pageable);
       return invoicesPage.map(invoiceMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public InvoiceDTO getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toDTO)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with id: " + id));
    }

    @Transactional
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) throws OrderNotFoundException {
       Invoice invoiceToUpdate = invoiceRepository.findById(id)
               .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with id: " + id));

       Orderss order = orderRepository.findById(invoiceDTO.getOrderId())
               .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + invoiceDTO.getOrderId()));

        Orderss existingOrder = orderRepository.findById(invoiceDTO.getOrderId()).
                orElseThrow(()-> new OrderNotFoundException("No any order with id "+
                        invoiceDTO.getOrderId() + " For create an invoice."));

        Optional<OrderItem> orderHasOrderItems = Optional.ofNullable(orderItemRepository
                .findByOrderId(invoiceDTO.getOrderId()).
                orElseThrow(() -> new OrderItemNotFoundException("No any order items in order id \"" +
                        invoiceDTO.getOrderId() +"\". First add items to order before issue an invoice.")));

        double orderTotalAmount = existingOrder.getAmount();

        double amountDifference = orderTotalAmount - invoiceDTO.getAmount();

        if (amountDifference > 0) {
            throw new AmountGreateOrLessException(
                    "Order total amount is greater than invoice amount by LKR " + amountDifference +
                            ". Add the remaining amount to issue the invoice.");
        } else if (amountDifference < 0) {
            throw new AmountGreateOrLessException(
                    "Order total amount is less than invoice amount by LKR " + Math.abs(amountDifference) +
                            ". Get the remaining amount to issue the invoice.");
        }

       invoiceToUpdate =invoiceMapper.toEntity(invoiceDTO);
       invoiceToUpdate.setId(id);
       Invoice updatedInvoice = invoiceRepository.save(invoiceToUpdate);
       return invoiceMapper.toDTO(updatedInvoice);
    }

    @Transactional(readOnly = true)
    public List<InvoiceDTO> searchInvoices(String searchKeyword) {
        List<Invoice> invoices = invoiceRepository.searchInvoices(searchKeyword);

        if(invoices.isEmpty()) {
            throw new InvoiceNotFoundException("Invoice not found with keyword: " + searchKeyword);
        }
        return invoices.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }
}