package com.example.CustomerView.repository;

import com.example.CustomerView.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT MAX(p.product_Id) FROM Product p")
    Integer findMaxProductId();

    @Query("SELECT MAX(p.product_Code) FROM Product p")
    String findMaxProductCode();
}
