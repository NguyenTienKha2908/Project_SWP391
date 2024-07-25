package com.jewelry.KiraJewelry.crawler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewelry.KiraJewelry.models.MaterialPriceList;


@Repository
public interface CrawlerRepository extends JpaRepository<MaterialPriceList, Integer> {
    
}
