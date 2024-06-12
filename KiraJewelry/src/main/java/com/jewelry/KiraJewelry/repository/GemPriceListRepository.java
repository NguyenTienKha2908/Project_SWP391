package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.GemPriceList;

@Repository
public interface GemPriceListRepository extends JpaRepository<GemPriceList, Integer> {

}
