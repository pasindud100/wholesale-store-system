package com.wholesale.demo.model;
import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int qty;
    private double price;
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "orderid", nullable = false)
    private Orderss order;

    @ManyToOne
    @JoinColumn(name = "productid", nullable = false)
    private Product product;

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

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
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

    public OrderItem() {
    }

    public OrderItem(Long id, int qty, double price, double subtotal, Orderss order, Product product) {
        this.id = id;
        this.qty = qty;
        this.price = price;
        this.subtotal = subtotal;
        this.order = order;
        this.product = product;
    }
}
