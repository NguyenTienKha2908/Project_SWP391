package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jewelry.KiraJewelry.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
}
