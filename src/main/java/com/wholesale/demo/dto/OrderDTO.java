package com.wholesale.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private Long customerId;
    private List<Long> orderItemIds;

    public OrderDTO(Long id, LocalDateTime orderDate, Long customerId, List<Long> orderItemIds) {
        this.id = id;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.orderItemIds = orderItemIds;
    }

    public OrderDTO() {
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getOrderItemIds() {
        return orderItemIds;
    }

    public void setOrderItemIds(List<Long> orderItemIds) {
        this.orderItemIds = orderItemIds;
    }
}