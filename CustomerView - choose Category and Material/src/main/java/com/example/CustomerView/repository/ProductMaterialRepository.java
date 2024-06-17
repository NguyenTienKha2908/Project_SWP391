package com.example.CustomerView.repository;

import com.example.CustomerView.entity.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Integer> {
}
