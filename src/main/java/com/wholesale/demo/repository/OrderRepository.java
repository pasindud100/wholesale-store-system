package com.wholesale.demo.repository;

import com.wholesale.demo.dto.FullOrderView;
import com.wholesale.demo.model.Customer;
import com.wholesale.demo.model.Invoice;
import com.wholesale.demo.model.Orderss;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orderss, Long> {

    Page<Orderss> findAll(Pageable pageable);

    @Query(value = "SELECT " +
            "o.id AS orderId, o.order_date AS orderDate, o.amount AS totalAmount, " +
            "c.id AS customerId, c.name AS customerName, " +
            "oi.id AS orderItemId, oi.productid AS productId, p.name AS productName, " +
            "oi.qty AS qty, oi.price AS price, oi.subtotal AS subtotal, " +
            "i.id AS invoiceId, i.invoice_date AS invoiceDate, i.amount AS invoiceAmount, " +
            "pay.id AS paymentId, pay.payment_date AS paymentDate, " +
            "pay.paid_amount AS paidAmount, pay.payment_method AS paymentMethod " +
            "FROM orderss o " +
            "JOIN customer c ON o.customerid = c.id " +
            "JOIN order_item oi ON o.id = oi.orderid " +
            "JOIN product p ON oi.productid = p.id " +
            "LEFT JOIN invoice i ON o.id = i.orderid " +
            "LEFT JOIN payment pay ON i.id = pay.invoiceid " +
            "WHERE (:orderId IS NULL OR o.id = :orderId) " +
            "AND (:customerId IS NULL OR c.id = :customerId) " +
            "AND (:invoiceId IS NULL OR i.id = :invoiceId) " +
            "AND (:paymentId IS NULL OR pay.id = :paymentId)",
            nativeQuery = true)
    List<FullOrderView> findFilteredOrders(
            @Param("orderId") Long orderId,
            @Param("customerId") Long customerId,
            @Param("invoiceId") Long invoiceId,
            @Param("paymentId") Long paymentId);

}
