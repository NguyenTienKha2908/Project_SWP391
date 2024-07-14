package com.jewelry.KiraJewelry.crawler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CrawlerRepository extends JpaRepository<CrawlerData, Integer> {
    
}
