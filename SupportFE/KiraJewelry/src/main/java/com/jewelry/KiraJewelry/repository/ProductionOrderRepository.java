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

        @Query("SELECT p FROM ProductionOrder p WHERE p.customer.customer_Id = :customer_Id")
        List<ProductionOrder> findProductionOrderByCustomerId(@Param("customer_Id") String customer_Id);

        // @Query("SELECT p FROM ProductionOrder p WHERE p.status = :status AND
        // p.sales_Staff_Name = :sales_Staff_Name")
        // List<ProductionOrder> findProductionOrderByStatusAndName(@Param("status")
        // String status,
        // @Param("sales_Staff_Name") String sales_Staff_Name);

        @Query("SELECT p FROM ProductionOrder p WHERE p.status = :status AND p.sales_Staff = :sales_Staff_Id")
        List<ProductionOrder> findProductionOrderByStatusAndName(@Param("status") String status,
                        @Param("sales_Staff_Id") String sales_Staff_Id);

        @Query("SELECT p FROM ProductionOrder p WHERE p.status = :status AND p.design_Staff = :design_Staff_Id")
        List<ProductionOrder> findProductionOrderByStatusAndDEId(@Param("status") String status,
                        @Param("design_Staff_Id") String design_Staff_Id);

        @Query("SELECT p FROM ProductionOrder p WHERE p.status = :status ")
        List<ProductionOrder> findProductionOrderByStatus(@Param("status") String status);

        @Query("SELECT p FROM ProductionOrder p ORDER BY p.production_Order_Id DESC")
        List<ProductionOrder> findTopByOrderByProduction_Order_IdDesc(Pageable pageable);

        @Query("SELECT p FROM ProductionOrder p WHERE p.customer.customer_Id = :customerId AND p.status IN :statuses ORDER BY p.date DESC")
        List<ProductionOrder> findByCustomerIdAndStatusIn(@Param("customerId") String customerId,
                        @Param("statuses") List<String> statuses, Pageable pageable);

        @Query("SELECT p FROM ProductionOrder p WHERE p.product.product_Id = :product_Id")
        ProductionOrder findProductionOrderByProductId(@Param("product_Id") int product_Id);

        @Query("SELECT COUNT(p) FROM ProductionOrder p")
        int countAll();

        @Query("SELECT COUNT(p) FROM ProductionOrder p WHERE p.status = 'Canceled'")
        int countByStatus(String status);

        @Query("SELECT SUM(p.o_Total_Amount) FROM ProductionOrder p WHERE MONTH(p.date) = MONTH(CURRENT_DATE) AND YEAR(p.date) = YEAR(CURRENT_DATE) AND p.status ='Delivered'")
        double sumTotalAmountByMonth();

        @Query("SELECT SUM(p.o_Total_Amount) FROM ProductionOrder p WHERE MONTH(p.date) = MONTH(CURRENT_DATE) - 1 AND YEAR(p.date) = YEAR(CURRENT_DATE)")
        double sumTotalAmountByLastMonth();

        List<ProductionOrder> findAll();
}