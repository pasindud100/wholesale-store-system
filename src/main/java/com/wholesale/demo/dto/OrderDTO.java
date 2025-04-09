package com.wholesale.demo.dto;

import java.time.LocalDateTime;

public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private Long customerId;

    public OrderDTO() {}

    public OrderDTO(Long id, LocalDateTime orderDate, Long customerId) {
        this.id = id;
        this.orderDate = orderDate;
        this.customerId = customerId;
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
}

