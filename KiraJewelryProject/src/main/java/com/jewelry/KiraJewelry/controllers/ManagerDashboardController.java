package com.jewelry.KiraJewelry.controllers;

import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.service.ProductionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ManagerDashboardController {

    @Autowired
    private ProductionOrderService productionOrderService;

    @GetMapping("/managerDashboard")
    public String showDashboard(Model model) {
        // Fetch total orders
        int totalOrders = productionOrderService.getTotalOrders();
        model.addAttribute("totalOrders", totalOrders);

        // Fetch total canceled orders
        int totalCanceledOrders = productionOrderService.getTotalCanceledOrders();
        model.addAttribute("totalCanceledOrders", totalCanceledOrders);

        // Fetch total revenue for the current month
        double totalRevenueThisMonth = productionOrderService.getTotalRevenueThisMonth();
        model.addAttribute("totalRevenueThisMonth", totalRevenueThisMonth);

        

        // Fetch all production orders
        List<ProductionOrder> productionOrders = productionOrderService.getAllProductionOrders();
        model.addAttribute("productionOrders", productionOrders);

        return "employee/manager/managerDashboard";
    }
}
