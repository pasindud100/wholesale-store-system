package com.wholesale.demo.dto;

public class OrderItemDTO {
    private int qty;
    private double price;
    private Long orderId;
    private Long productId;
    public OrderItemDTO() {
    }

    public OrderItemDTO(int qty, double price, Long orderId, Long productId) {
        this.qty = qty;
        this.price = price;
        this.orderId = orderId;
        this.productId = productId;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}