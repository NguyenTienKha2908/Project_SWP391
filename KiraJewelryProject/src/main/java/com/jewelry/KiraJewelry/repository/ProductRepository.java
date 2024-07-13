package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jewelry.KiraJewelry.models.Product;
//da get
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT MAX(p.product_Id) FROM Product p")
    Integer findMaxProductId();

    @Query("SELECT MAX(p.product_Code) FROM Product p")
    String findMaxProductCode();

    // @Query("SELECT p FROM Product p WHERE p.product.product_Id = :product_Id")
    // Product getProductByProductId(int product_Id);
}

