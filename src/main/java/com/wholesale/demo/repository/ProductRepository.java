package com.wholesale.demo.repository;

import com.wholesale.demo.model.Customer;
import com.wholesale.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//
//    Page<Product> findAll(Pageable pageable);
//
//    @Query("SELECT products FROM Product products WHERE " +
//            "LOWER(products.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(products.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(products.stock) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//            "LOWER(products.price) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//    List<Product> searchProducts(@Param("keyword") String keyword);
}