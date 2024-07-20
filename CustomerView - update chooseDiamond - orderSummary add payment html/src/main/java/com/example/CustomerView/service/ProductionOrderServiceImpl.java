package com.example.CustomerView.service;

import com.example.CustomerView.entity.ProductionOrder;
import com.example.CustomerView.repository.ProductionOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionOrderServiceImpl implements ProductionOrderService {

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Override
    public List<ProductionOrder> getAllProductionOrders() {
        return productionOrderRepository.findAll();
    }

    @Override
    public ProductionOrder getProductionOrderById(String id) {
        return productionOrderRepository.findById(id).orElse(null);
    }

    @Override
    public void saveProductionOrder(ProductionOrder productionOrder) {
        if (productionOrder.getProduction_order_id() == null) {
            productionOrder.setProduction_order_id(generateNewOrderId());
        }
        productionOrderRepository.save(productionOrder);
    }

    @Override
    public void deleteProductionOrderById(String id) {
        productionOrderRepository.deleteById(id);
    }

    @Override
    public String generateNewOrderId() {
        String maxOrderId = productionOrderRepository.findMaxOrderId();
        if (maxOrderId == null) {
            return "POI00001";
        }
        int numericPart = Integer.parseInt(maxOrderId.substring(3));
        int newNumericPart = numericPart + 1;
        return "POI" + String.format("%05d", newNumericPart);
    }
}
