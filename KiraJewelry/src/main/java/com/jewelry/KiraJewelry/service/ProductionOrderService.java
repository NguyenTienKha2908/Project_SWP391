package com.jewelry.KiraJewelry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.repository.ProductionOrderRepository;

@Service
public class ProductionOrderService {

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

  public List<ProductionOrder> getProductionOrderByStatusAndName(String status,
      String employee_Name) {
    return productionOrderRepository.findProductionOrderByStatusAndName(status,
        employee_Name);
  }

  public void deleteProductionOrderById(String productionOrderId) {
    productionOrderRepository.deleteById(productionOrderId);
  }

  public List<ProductionOrder> getProductionOrderByStatus(String status) {
    return productionOrderRepository.findProductionOrderByStatus(status);
  }

  public List<ProductionOrder> getProductionOrderByCustomerId(String customer_Id) {
    return productionOrderRepository.findProductionOrderByCustomerId(customer_Id);
  }

  public ProductionOrder getTopByOrderByProduction_Order_IdDesc() {
    Pageable pageable = PageRequest.of(0, 1);
    List<ProductionOrder> results = productionOrderRepository.findTopByOrderByProduction_Order_IdDesc(pageable);
    return results.isEmpty() ? null : results.get(0);
  }

  // public Optional<ProductionOrder> getTopByOrderByProduction_Order_IdDesc() {
  // return productionOrderRepository.findTopByOrderByProduction_Order_IdDesc();
  // }

  public ProductionOrder getProductionOrderByProductId(int productId) {
    return productionOrderRepository.findProductionOrderByProductId(productId);
  }

  public ProductionOrder findLatestByCustomerIdAndStatusIn(String customerId, List<String> statuses) {
    Pageable pageable = PageRequest.of(0, 1);
    List<ProductionOrder> results = productionOrderRepository.findByCustomerIdAndStatusIn(customerId, statuses,
        pageable);
    return results.isEmpty() ? null : results.get(0);
  }

}