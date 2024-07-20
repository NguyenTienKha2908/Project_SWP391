package com.example.CustomerView.repository;

import com.example.CustomerView.entity.ProductionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, String> {
    
    @Query("SELECT MAX(poi.production_order_id) FROM ProductionOrder poi")
    String findMaxOrderId();
}
