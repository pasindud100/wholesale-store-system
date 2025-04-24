package com.wholesale.demo.repository;

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

    @Query("SELECT orders FROM Orderss orders WHERE " +
            "CAST(orders.id AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(orders.amount AS string) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(orders.customer AS string) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Orderss> serarchOrders(@Param("keyword") String keyword);
}
