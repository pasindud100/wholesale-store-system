package com.wholesale.demo.repository;

import com.wholesale.demo.model.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByOrderId(Long orderId);
    Page<Invoice> findAll(Pageable pageable);

    @Query("SELECT invoice FROM Invoice invoice WHERE " +
            "CAST(invoice.id AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(invoice.invoiceDate AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(invoice.amount AS string) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Invoice> searchInvoices(@Param("keyword") String keyword);
}