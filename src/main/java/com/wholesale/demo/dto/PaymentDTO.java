package com.wholesale.demo.dto;

import java.time.LocalDateTime;

public class PaymentDTO {

    private Long id;
    private LocalDateTime paymentDate;
    private Double paidAmount;
    private String paymentMethod;
    private Long orderId;
    private Long invoiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public double getPaidAmount() {
        return paidAmount;
    }
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public PaymentDTO() {
    }

    public PaymentDTO(Long id, Long invoiceId, Long orderId, LocalDateTime paymentDate, Double paidAmount, String paymentMethod) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.orderId = orderId;
        this.paymentDate = paymentDate;
        this.paidAmount = paidAmount;
        this.paymentMethod = paymentMethod;
    }
}
