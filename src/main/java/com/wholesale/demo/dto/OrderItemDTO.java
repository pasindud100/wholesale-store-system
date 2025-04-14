package com.wholesale.demo.dto;

public class OrderItemDTO {
    private long id;
    private int qty;
    private double price;
    private Long productId;

    public OrderItemDTO() {
    }

    public OrderItemDTO(long id, int qty, double price, Long productId) {
        this.id = id;
        this.qty = qty;
        this.price = price;
        this.productId = productId;
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
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}