package com.wholesale.demo.dto;

public class OrderItemDTO {
    private Long id;
    private int qty;
    private double price;
    private double subtotal;
    private Long productId;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long id, int qty, double price, double subtotal, Long productId) {
        this.id = id;
        this.qty = qty;
        this.price = price;
        this.subtotal = subtotal;
        this.productId = productId;
    }

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
