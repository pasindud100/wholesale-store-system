package com.wholesale.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private double amount;
    private Long customerId;
    private List<OrderItemDTO> orderItems;
    private InvoiceDTO invoice;
    private PaymentDTO payment;

    public OrderDTO() {}

    public OrderDTO(Long id, LocalDateTime orderDate, double amount, Long customerId, List<OrderItemDTO> orderItems, InvoiceDTO invoice, PaymentDTO payment) {
        this.id = id;
        this.orderDate = orderDate;
        this.amount = amount;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.invoice = invoice;
        this.payment = payment;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoice = invoice;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }
}
