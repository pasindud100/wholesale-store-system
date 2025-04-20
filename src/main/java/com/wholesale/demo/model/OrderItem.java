package com.wholesale.demo.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int qty;
    private double price;

    @ManyToOne
    @JoinColumn(name = "orderID", nullable = false)
    private Orderss order;

    @ManyToOne
    @JoinColumn(name = "productID", nullable = false)
    private Product product;

    // New field for subtotal
    private double subtotal;

    public OrderItem() {
    }

    public OrderItem(long id, int qty, double price, Orderss order, Product product) {
        this.id = id;
        this.qty = qty;
        this.price = price;
        this.order = order;
        this.product = product;
        this.subtotal = calculateSubtotal(); // Calculate subtotal when creating the order item
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
        this.subtotal = calculateSubtotal(); // Update subtotal if quantity changes
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.subtotal = calculateSubtotal(); // Update subtotal if price changes
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

    // Method to calculate the subtotal for the order item
    public double calculateSubtotal() {
        return price * qty;
    }

    public double getSubtotal() {
        return subtotal;
    }

    // Setter for subtotal (if needed)
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}