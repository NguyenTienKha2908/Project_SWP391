// package com.example.demo.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import com.example.demo.dto.ProductionOrderDTO;
// import com.example.demo.service.ProductionOrderService;

// @Controller
// public class ProductionOrderController {

//     @Autowired
//     private ProductionOrderService productionOrderService;

//     @PostMapping("/request")
//     public String createProductionOrder(@ModelAttribute ProductionOrderDTO productionOrderDTO) {
//         productionOrderService.saveProductionOrder(productionOrderDTO);
//         return "redirect:/customer/index";
//     }
// }
