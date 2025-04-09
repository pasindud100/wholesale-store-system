package com.wholesale.demo.repository;

import com.wholesale.demo.model.Orderss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orderss, Long> {
    List<Orderss> findByCustomerId(Long customerId);
}
