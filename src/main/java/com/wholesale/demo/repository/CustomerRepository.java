package com.wholesale.demo.repository;

import com.wholesale.demo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findAll(Pageable pageable);

    // Custom query to search customers based on multiple fields like name, address, phone, and email.
    @Query("SELECT customer FROM Customer customer WHERE " +
            "LOWER(customer.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(customer.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(customer.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(customer.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Customer> searchCustomers(@Param("keyword") String keyword);
}
