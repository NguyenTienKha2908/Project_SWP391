package com.jewelry.KiraJewelry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.jewelry.KiraJewelry.models.ProductionOrder;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, String> {
    @Query("SELECT p FROM ProductionOrder p WHERE p.production_Order_Id = :production_Order_Id")
    ProductionOrder findProductionOrderById(@Param("production_Order_Id") String production_Order_Id);

    @Query("SELECT p FROM ProductionOrder p WHERE p.status = :status AND p.sales_Staff_Name = :sales_Staff_Name")
    List<ProductionOrder> findProductionOrderByStatusAndName(@Param("status") String status,
            @Param("sales_Staff_Name") String sales_Staff_Name);

}
