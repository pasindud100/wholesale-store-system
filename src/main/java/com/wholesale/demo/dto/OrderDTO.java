package com.wholesale.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private Long customerId;
    private List<OrderItemDTO> orderItems;
    private Long invoiceId;
    private Long paymentId;

    public OrderDTO() {
    }

    public OrderDTO(Long id, LocalDateTime orderDate, Long customerId, List<OrderItemDTO> orderItems, Long invoiceId, Long paymentId) {
        this.id = id;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.invoiceId = invoiceId;
        this.paymentId = paymentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}