package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jewelry.KiraJewelry.models.ProductMaterial;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Integer> {
}

