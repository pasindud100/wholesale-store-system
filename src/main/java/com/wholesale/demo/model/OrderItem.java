package com.wholesale.demo.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int qty;
    private double price;

    @ManyToOne
    @JoinColumn(name = "orderid", nullable = false)
    private Orderss order;

    @ManyToOne
    @JoinColumn(name = "productid", nullable = false)
    private Product product;

    @Column(name = "subtotal")
    private double subtotal;

    public OrderItem() {
    }

    public OrderItem(int qty, double price, Orderss order, Product product) {
        this.qty = qty;
        this.price = price;
        this.order = order;
        this.product = product;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Orderss getOrder() {
        return order;
    }

    public void setOrder(Orderss order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSubtotal() {
        return qty * price; // Calculate subtotal
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal; // Set subtotal directly if needed
    }
}