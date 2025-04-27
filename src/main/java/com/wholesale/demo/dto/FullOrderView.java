package com.wholesale.demo.dto;

import java.util.Date;

public interface FullOrderView {
    Long getOrderId();
    Date getOrderDate();
    Double getTotalAmount();
    Long getCustomerId();
    String getCustomerName();

    Long getOrderItemId();
    Long getProductId();
    String getProductName();
    int getQty();
    double getPrice();
    double getSubtotal();

    Long getInvoiceId();
    Date getInvoiceDate();
    Double getInvoiceAmount();

    Long getPaymentId();
    Date getPaymentDate();
    Double getPaidAmount();
    String getPaymentMethod();
}

