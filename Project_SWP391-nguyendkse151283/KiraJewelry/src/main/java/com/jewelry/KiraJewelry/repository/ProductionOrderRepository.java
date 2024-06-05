package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.jewelry.KiraJewelry.models.ProductionOrder;

import java.util.List;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, String> {
    @Query("SELECT p FROM ProductionOrder p WHERE p.productionOrderId = :productionOrderId")
    ProductionOrder findProductionOrderById(@Param("productionOrderId") String productionOrderId);

    @Query("SELECT p FROM ProductionOrder p WHERE p.status = :status AND p.salesStaffName = :salesStaffName")
    List<ProductionOrder> findProductionOrderByStatusAndSalesStaffName(@Param("status") String status,
            @Param("salesStaffName") String salesStaffName);

    @Query("SELECT p FROM ProductionOrder p WHERE p.status = :status")
    List<ProductionOrder> findProductionOrderByStatus(@Param("status") String status);
}
