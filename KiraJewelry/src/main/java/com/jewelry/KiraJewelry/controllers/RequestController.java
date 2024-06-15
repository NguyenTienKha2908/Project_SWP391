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
import com.jewelry.KiraJewelry.service.FilesStorageService;
import com.jewelry.KiraJewelry.service.ImageService;
import com.jewelry.KiraJewelry.service.ProductionOrderService;
import jakarta.servlet.http.HttpSession;

@Controller
public class RequestController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductionOrderService productionOrderService;

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
            @RequestParam("description") String description,
            HttpSession session) {
        // Get user info from session
        String customerId = (String) session.getAttribute("customerId");

        // Generate production_Order_Id
        // String productionOrderId =
        // productionOrderService.generateProductionOrderId();

        // Create current date
        Date currentDate = new Date();

        // Create ProductionOrder object from form data
        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setProduction_Order_Id("POI001");
        productionOrder.setDate(currentDate);
        productionOrder.setCustomer_Id(customerId);
        productionOrder.setCategory_id(categoryId);
        productionOrder.setProduct_Size(productSize);
        productionOrder.setDescription(description);
        productionOrder.setStatus("Created");

        // Set other attributes to null or 0
        productionOrder.setQ_Diamond_Amount(0);
        productionOrder.setQ_Material_Amount(0);
        productionOrder.setQ_Production_Amount(0);
        productionOrder.setQ_Total_Amount(0);
        productionOrder.setO_Diamond_Amount(0);
        productionOrder.setO_Material_Amount(0);
        productionOrder.setO_Production_Amount(0);
        productionOrder.setO_Total_Amount(0);
        productionOrder.setSales_Staff_Id(null);
        productionOrder.setDesign_Staff_Id(null);
        productionOrder.setProduction_Staff_Id(null);

        // Save ProductionOrder to database
        try {
            productionOrderRepository.save(productionOrder);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception (log it, show error message, etc.)
            return "error";
        }

        // Redirect to success page
        return "success";
    }

    @Autowired
    FilesStorageService storageService;

    @Autowired
    ImageService imageService;

}
