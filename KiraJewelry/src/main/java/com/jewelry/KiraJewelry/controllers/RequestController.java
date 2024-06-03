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

    @GetMapping("/request")
    public String requestPage(Model model, HttpSession session) {
        if (session.getAttribute("customerName") == null) {
            return "redirect:/login";
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        return "request";
    }

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

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
        // Lấy thông tin người dùng từ session
        String customerId = (String) session.getAttribute("customerId");

        // Tạo production_Order_Id theo POI00001, POI00002, ...
        // Đếm số lượng đơn hàng hiện tại
        long currentOrderCount = productionOrderRepository.count();

        String productionOrderId = "POI" + String.format("%05d", currentOrderCount + 1);

        // Tạo ngày hiện tại
        Date currentDate = new Date();

        // Tạo đối tượng ProductionOrder từ dữ liệu nhận được từ form
        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setProduction_Order_Id(productionOrderId);
        productionOrder.setDate(currentDate);
        productionOrder.setCustomer_Id(customerId);
        productionOrder.setCategory_Id(categoryId);
        productionOrder.setMaterial_Name(materialName);
        productionOrder.setMaterial_Color(materialColor);
        productionOrder.setMaterial_Weight(materialWeight);
        productionOrder.setGem_Name(gemName);
        productionOrder.setGem_Color(gemColor);
        productionOrder.setGem_Weight(gemWeight);
        productionOrder.setProduct_Size(productSize);
        productionOrder.setDescription(description);
        productionOrder.setStatus("Created");

        // Đặt các thuộc tính còn lại là null hoặc 0
        productionOrder.setDiamond_Amount(0);
        productionOrder.setMaterial_Amount(0);
        productionOrder.setProduction_Amount(0);
        productionOrder.setSide_Gem_Cost(0);
        productionOrder.setTotal_Amount(0);
        productionOrder.setSales_Staff_Name(null);
        productionOrder.setDesign_Staff_Name(null);
        productionOrder.setProduction_Staff_Name(null);
        productionOrder.setProduct(null);

        // Lưu đối tượng ProductionOrder vào cơ sở dữ liệu
        productionOrderRepository.save(productionOrder);

        // Redirect đến trang thành công
        return "success";
    }

}
