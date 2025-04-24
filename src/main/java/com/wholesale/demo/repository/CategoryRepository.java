package com.wholesale.demo.repository;

import com.wholesale.demo.model.Category;
import com.wholesale.demo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Page<Category> findAll(Pageable pageable);

    @Query("SELECT category FROM Category category WHERE " +
            "LOWER(category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Category> searchCategory(@Param("keyword") String keyword);
}