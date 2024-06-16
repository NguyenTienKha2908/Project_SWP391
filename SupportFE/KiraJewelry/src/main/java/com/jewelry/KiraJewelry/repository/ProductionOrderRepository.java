package com.jewelry.KiraJewelry.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import com.jewelry.KiraJewelry.models.ProductionOrder;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, String> {
    @Query("SELECT p FROM ProductionOrder p WHERE p.production_Order_Id = :production_Order_Id")
    ProductionOrder findProductionOrderById(@Param("production_Order_Id") String production_Order_Id);

    @Query("SELECT p FROM ProductionOrder p WHERE p.customer_Id = :customer_Id")
    List<ProductionOrder> findProductionOrderByCustomerId(@Param("customer_Id") String customer_Id);

    // @Query("SELECT p FROM ProductionOrder p WHERE p.status = :status AND p.sales_Staff_Name = :sales_Staff_Name")
    // List<ProductionOrder> findProductionOrderByStatusAndName(@Param("status") String status,
    //         @Param("sales_Staff_Name") String sales_Staff_Name);

    @Query("SELECT p FROM ProductionOrder p WHERE p.status = :status AND p.sales_Staff_Id = :sales_Staff_Id")
    List<ProductionOrder> findProductionOrderByStatusAndName(@Param("status") String status,
            @Param("sales_Staff_Id") String sales_Staff_Id);


    @Query("SELECT p FROM ProductionOrder p WHERE p.status = :status ")
    List<ProductionOrder> findProductionOrderByStatus(@Param("status") String status);

    @Query("SELECT p FROM ProductionOrder p ORDER BY p.production_Order_Id DESC")
    List<ProductionOrder> findTopByOrderByProduction_Order_IdDesc(Pageable pageable);
}