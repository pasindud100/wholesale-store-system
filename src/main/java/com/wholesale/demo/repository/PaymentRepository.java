package com.wholesale.demo.repository;

import com.wholesale.demo.model.Invoice;
import com.wholesale.demo.model.Orderss;
import com.wholesale.demo.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderId(Long orderId);
    Optional<Payment> findByInvoiceId(Long invoiceId);

    Page<Payment> findAll(Pageable pageable);

    @Query("SELECT payments FROM Payment payments WHERE " +
            "CAST(payments.id AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(payments.paymentDate AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(payments.paymentMethod) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(payments.order AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(payments.invoice AS string) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Payment> searchPayments(@Param("keyword") String keyword);
}