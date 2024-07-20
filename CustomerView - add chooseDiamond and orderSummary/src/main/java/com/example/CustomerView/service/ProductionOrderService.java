package com.example.CustomerView.service;

import com.example.CustomerView.entity.ProductionOrder;

import java.util.List;

public interface ProductionOrderService {
    List<ProductionOrder> getAllProductionOrders();
    ProductionOrder getProductionOrderById(String id);
    void saveProductionOrder(ProductionOrder productionOrder);
    void deleteProductionOrderById(String id);
    String generateNewOrderId();
}
