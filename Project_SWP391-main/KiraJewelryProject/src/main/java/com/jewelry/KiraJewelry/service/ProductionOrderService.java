package com.jewelry.KiraJewelry.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.repository.ProductionOrderRepository;

@Service
public class ProductionOrderService {
  @Autowired
  private static final Logger log = LoggerFactory.getLogger(ProductionOrderService.class);
  @Autowired
  private ProductionOrderRepository productionOrderRepository;

  public List<ProductionOrder> getAllProductionOrders() {
    return productionOrderRepository.findAll();
  }

  public ProductionOrder getProductionOrderById(String id) {
    return productionOrderRepository.findProductionOrderById(id);
  }

  public void saveProductionOrder(ProductionOrder productionOrder) {
    productionOrderRepository.save(productionOrder);
  }

  public List<ProductionOrder> getProductionOrderByStatusAndId(String status,
      String employee_id) {
    return productionOrderRepository.findProductionOrderByStatusAndId(status,
        employee_id);
  }

  public List<ProductionOrder> getProductionOrderByStatusAndDEId(String status,
      String employee_Name) {
    return productionOrderRepository.findProductionOrderByStatusAndDEId(status,
        employee_Name);
  }

  public void deleteProductionOrderById(String productionOrderId) {
    productionOrderRepository.deleteById(productionOrderId);
  }

  public List<ProductionOrder> getProductionOrderByStatus(String status) {
    return productionOrderRepository.findProductionOrderByStatus(status);
  }

  public ProductionOrderService(ProductionOrderRepository productionOrderRepository) {
    this.productionOrderRepository = productionOrderRepository;
  }

  public List<ProductionOrder> getAllOrdersByStatus(String status, String sortDirection) {
    if ("ASC".equalsIgnoreCase(sortDirection)) {
      return productionOrderRepository.findProductionOrderByStatusASC(status);
    } else {
      return productionOrderRepository.findProductionOrderByStatusDESC(status);
    }
  }

  // public Page<ProductionOrder> getAllOrdersByStatus(String status, Pageable
  // pageable) {

  // return productionOrderRepository.findProductionOrderByStatusPage(status,
  // pageable);

  // }

  public List<ProductionOrder> getProductionOrderByCustomerId(String customer_Id) {
    return productionOrderRepository.findProductionOrderByCustomerId(customer_Id);
  }

  public ProductionOrder getTopByOrderByProduction_Order_IdDesc() {
    Pageable pageable = PageRequest.of(0, 1);
    List<ProductionOrder> results = productionOrderRepository.findTopByOrderByProduction_Order_IdDesc(pageable);
    return results.isEmpty() ? null : results.get(0);
  }

  public ProductionOrder findLatestByCustomerIdAndStatusIn(String customerId, List<String> statuses) {
    Pageable pageable = PageRequest.of(0, 1);
    List<ProductionOrder> results = productionOrderRepository.findByCustomerIdAndStatusIn(customerId, statuses,
        pageable);
    return results.isEmpty() ? null : results.get(0);
  }

  public ProductionOrder getProductionOrderByProductId(int productId) {
    return productionOrderRepository.findProductionOrderByProductId(productId);
  }

  public int getTotalOrders() {
    return productionOrderRepository.countAll();
  }

  public int getTotalCanceledOrders() {
    return productionOrderRepository.countByStatus("Canceled");
  }

  public double getTotalRevenueThisMonth() {
    return productionOrderRepository.sumTotalAmountByMonth();
  }

  public double getRevenueChangePercentage() {
    double lastMonthRevenue = productionOrderRepository.sumTotalAmountByLastMonth();
    double thisMonthRevenue = getTotalRevenueThisMonth();

    if (lastMonthRevenue == 0) {
      return thisMonthRevenue == 0 ? 0 : 100;
    }

    return ((thisMonthRevenue - lastMonthRevenue) / lastMonthRevenue) * 100;
  }

  public List<ProductionOrder> getProductionOrderByDEEmployeeId(String design_Staff_Id) {
    return productionOrderRepository.getProductionOrderByDEId(design_Staff_Id);
  }

  public List<ProductionOrder> getProductionOrderByPREmployeeId(String production_Staff_Id) {
    return productionOrderRepository.getProductionOrderByPRId(production_Staff_Id);
  }
}