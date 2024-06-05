package com.jewelry.KiraJewelry.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.repository.ProductionOrderRepository;
import com.jewelry.KiraJewelry.service.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RequestController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @GetMapping("/request")
    public String requestPage(Model model, HttpSession session) {
        if (session.getAttribute("customerName") == null) {
            return "redirect:/login";
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        return "request";
    }

    @PostMapping("/submitRequest")
    public String submitRequest(@RequestParam("category") int categoryId,
                                @RequestParam("productSize") int productSize,
                                @RequestParam("material_Name") String materialName,
                                @RequestParam("material_Color") String materialColor,
                                @RequestParam("material_Weight") double materialWeight,
                                @RequestParam("gem_Name") String gemName,
                                @RequestParam("gem_Color") String gemColor,
                                @RequestParam("gem_Weight") double gemWeight,
                                @RequestParam("description") String description,
                                HttpSession session) {
        // Retrieve customer ID from session
        String customerId = (String) session.getAttribute("customerId");

        // Generate new production order ID
        long currentOrderCount = productionOrderRepository.count();
        String productionOrderId = "POI" + String.format("%05d", currentOrderCount + 1);

        // Set current date
        Date currentDate = new Date();

        // Create and populate ProductionOrder object
        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setProductionOrderId(productionOrderId);
        productionOrder.setDate(currentDate);
        productionOrder.setCustomerId(customerId);
        productionOrder.setCategoryId(categoryId);
        productionOrder.setMaterialName(materialName);
        productionOrder.setMaterialColor(materialColor);
        productionOrder.setMaterialWeight(materialWeight);
        productionOrder.setGemName(gemName);
        productionOrder.setGemColor(gemColor);
        productionOrder.setGemWeight(gemWeight);
        productionOrder.setProductSize(productSize);
        productionOrder.setDescription(description);
        productionOrder.setStatus("created");

        // Set default values for other fields
        productionOrder.setDiamondAmount(0.0);
        productionOrder.setMaterialAmount(0.0);
        productionOrder.setProductionAmount(0.0);
        productionOrder.setSideMaterialCost(0.0);
        productionOrder.setSideGemCost(0.0);
        productionOrder.setTotalAmount(0.0);
        productionOrder.setSalesStaffName(null);
        productionOrder.setDesignStaffName(null);
        productionOrder.setProductionStaffName(null);
        productionOrder.setProduct(null);

        // Save the ProductionOrder to the database
        productionOrderRepository.save(productionOrder);

        // Redirect to success page
        return "success";
    }
}
