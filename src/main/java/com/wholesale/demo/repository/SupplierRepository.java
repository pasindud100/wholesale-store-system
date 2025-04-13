package com.wholesale.demo.repository;

import com.wholesale.demo.model.Customer;
import com.wholesale.demo.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT suppliers FROM Supplier suppliers WHERE " +
            "LOWER(suppliers.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(suppliers.contactPerson) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(suppliers.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(suppliers.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Supplier> searchSuppliers(@Param("keyword") String keyword);
}
