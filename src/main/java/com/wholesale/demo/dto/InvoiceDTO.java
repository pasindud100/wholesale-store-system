package com.wholesale.demo.dto;

import java.time.LocalDateTime;

public class InvoiceDTO {
    private Long id;
    private LocalDateTime invoiceDate;
    private Long orderId;
    double amount;

    public InvoiceDTO() {
    }

    public InvoiceDTO(Long id, LocalDateTime invoiceDate, Long orderId, double amount) {
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.orderId = orderId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}