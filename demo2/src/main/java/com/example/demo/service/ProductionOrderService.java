// package com.example.demo.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import com.example.demo.dto.ProductionOrderDTO;
// import com.example.demo.models.ProductionOrder;

// @Service
// public class ProductionOrderService {

//     @Autowired
//     private ProductionOrderRepository productionOrderRepository;

//     public void saveProductionOrder(ProductionOrderDTO dto) {
//         ProductionOrder order = new ProductionOrder();
//         order.setCustomerName(dto.getFullName());
//         order.setPhoneNumber(dto.getPhoneNumber());
//         order.setCategory(dto.getCategory());
//         order.setProductSize(dto.getProductSize());
//         order.setMaterialName(dto.getMaterial());
//         order.setMaterialColor(dto.getColor());
//         order.setMaterialWeight(dto.getWeight());
//         order.setGemName(dto.getGemstone());
//         order.setGemColor(dto.getGemColor());
//         order.setGemWeight(dto.getGemWeight());
//         order.setDescription(dto.getDescription());
//         // Set other properties if necessary

//         productionOrderRepository.save(order);
//     }
// }
