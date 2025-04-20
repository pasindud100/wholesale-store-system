package com.wholesale.demo.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime invoiceDate;
    private double amount;

    @OneToOne
    @JoinColumn(name = "orderID", unique = true, nullable = false)
    private Orderss order;

    @OneToOne(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    public Invoice() {
    }

    public Invoice(Long id, LocalDateTime invoiceDate, double amount, Orderss order, Payment payment) {
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.amount = amount;
        this.order = order;
        this.payment = payment;
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

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Orderss getOrder() {
        return order;
    }

    public void setOrder(Orderss order) {
        this.order = order;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}