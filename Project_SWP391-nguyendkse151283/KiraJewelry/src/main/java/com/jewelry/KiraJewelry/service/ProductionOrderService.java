package com.jewelry.KiraJewelry.service;

import com.jewelry.KiraJewelry.exceptions.ResourceNotFoundException;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.repository.ProductionOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  public List<ProductionOrder> getProductionOrderByStatusAndSalesStaffName(String status, String salesStaffName) {
    return productionOrderRepository.findProductionOrderByStatusAndSalesStaffName(status, salesStaffName);
  }

  public List<ProductionOrder> getProductionOrdersByStatus(String status) {
    return productionOrderRepository.findProductionOrderByStatus(status);
  }

  public ProductionOrder updateProductionOrder(String id, ProductionOrder updatedOrder) {
    Optional<ProductionOrder> optionalOrder = productionOrderRepository.findById(id);
    if (optionalOrder.isPresent()) {
      ProductionOrder existingOrder = optionalOrder.get();
      existingOrder.setMaterialName(updatedOrder.getMaterialName());
      existingOrder.setMaterialColor(updatedOrder.getMaterialColor());
      existingOrder.setMaterialWeight(updatedOrder.getMaterialWeight());
      existingOrder.setGemName(updatedOrder.getGemName());
      existingOrder.setGemColor(updatedOrder.getGemColor());
      existingOrder.setGemWeight(updatedOrder.getGemWeight());
      existingOrder.setProductSize(updatedOrder.getProductSize());
      existingOrder.setDescription(updatedOrder.getDescription());
      existingOrder.setStatus("quoted");
      return productionOrderRepository.save(existingOrder);
    } else {
      throw new ResourceNotFoundException("ProductionOrder", "id", id);
    }
  }

  public void managerAction(String id, String action) {
    Optional<ProductionOrder> optionalOrder = productionOrderRepository.findById(id);
    if (optionalOrder.isPresent()) {
      ProductionOrder order = optionalOrder.get();
      if (action.equals("accept")) {
        order.setStatus("accepted");
      } else {
        order.setStatus("requested");
      }
      productionOrderRepository.save(order);
    } else {
      throw new ResourceNotFoundException("ProductionOrder", "id", id);
    }
  }

  public void customerAction(String id, String action) {
    Optional<ProductionOrder> optionalOrder = productionOrderRepository.findById(id);
    if (optionalOrder.isPresent()) {
      ProductionOrder order = optionalOrder.get();
      if (action.equals("accept")) {
        order.setStatus("ordered");
      } else {
        order.setStatus("requested");
      }
      productionOrderRepository.save(order);
    } else {
      throw new ResourceNotFoundException("ProductionOrder", "id", id);
    }
  }

  public ProductionOrder updateOrderStatus(String id, String status) {
    Optional<ProductionOrder> optionalOrder = productionOrderRepository.findById(id);
    if (optionalOrder.isPresent()) {
      ProductionOrder existingOrder = optionalOrder.get();
      existingOrder.setStatus(status);
      return productionOrderRepository.save(existingOrder);
    } else {
      throw new ResourceNotFoundException("ProductionOrder", "id", id);
    }
  }

  public void acceptOrder(String id) {
    Optional<ProductionOrder> optionalOrder = productionOrderRepository.findById(id);
    if (optionalOrder.isPresent()) {
      ProductionOrder order = optionalOrder.get();
      order.setStatus("accepted");
      productionOrderRepository.save(order);
    } else {
      throw new ResourceNotFoundException("ProductionOrder", "id", id);
    }
  }
}
