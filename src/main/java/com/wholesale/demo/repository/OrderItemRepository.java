package com.wholesale.demo.repository;

import com.wholesale.demo.model.Invoice;
import com.wholesale.demo.model.OrderItem;
import com.wholesale.demo.model.Orderss;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Orderss order);
    Optional<OrderItem> findByOrderId(Long orderId);
    Page<OrderItem> findAll(Pageable pageable);

    @Query("SELECT orderItems FROM OrderItem orderItems WHERE " +
            "CAST(orderItems.id AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(orderItems.price AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(orderItems.qty AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(orderItems.subtotal AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(orderItems.product AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(orderItems.order AS string) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<OrderItem> searchOrderItems(@Param("keyword") String keyword);
}
