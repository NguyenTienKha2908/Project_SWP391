package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.MaterialPriceList;

@Repository
public interface MaterialPriceListRepository extends JpaRepository<MaterialPriceList, Integer> {

}