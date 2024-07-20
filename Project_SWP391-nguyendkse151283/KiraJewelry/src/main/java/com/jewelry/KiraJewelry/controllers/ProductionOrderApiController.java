// package com.jewelry.KiraJewelry.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.jewelry.KiraJewelry.models.ProductionOrder;
// import com.jewelry.KiraJewelry.service.ProductionOrderService;

// import java.util.List;

// @RestController
// @RequestMapping("/production-orders/api")
// public class ProductionOrderApiController {

//     @Autowired
//     private ProductionOrderService productionOrderService;

//     @GetMapping("/requested")
//     public List<ProductionOrder> getRequestedProductionOrders() {
//         return productionOrderService.getProductionOrdersByStatus("requested");
//     }
// }
