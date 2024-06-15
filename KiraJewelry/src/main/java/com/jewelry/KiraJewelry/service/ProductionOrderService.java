package com.jewelry.KiraJewelry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

  // public List<ProductionOrder> getProductionOrderByStatusAndName(String status,
  // String employee_Name) {
  // return productionOrderRepository.findProductionOrderByStatusAndName(status,
  // employee_Name);
  // }

  public void deleteProductionOrderById(String productionOrderId) {
    productionOrderRepository.deleteById(productionOrderId);
  }

  public List<ProductionOrder> getProductionOrderByStatus(String status) {
    return productionOrderRepository.findProductionOrderByStatus(status);
  }

  public List<ProductionOrder> getProductionOrderByCustomerId(String customer_Id) {
    return productionOrderRepository.findProductionOrderByCustomerId(customer_Id);
  }

  // public String generateProductionOrderId() {
  //   String lastId = productionOrderRepository.findLastProductionOrderId();
  //   if (lastId == null || lastId.isEmpty()) {
  //     return "POI001";
  //   } else {
  //     int lastNum = Integer.parseInt(lastId.substring(3));
  //     int newNum = lastNum + 1;
  //     return "POI" + String.format("%03d", newNum);
  //   }
  // }
}