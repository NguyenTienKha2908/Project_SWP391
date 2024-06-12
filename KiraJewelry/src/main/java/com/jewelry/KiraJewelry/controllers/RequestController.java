package com.jewelry.KiraJewelry.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.repository.ProductionOrderRepository;
import com.jewelry.KiraJewelry.service.CategoryService;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.EmployeeService;
import com.jewelry.KiraJewelry.service.FilesStorageService;
import com.jewelry.KiraJewelry.service.ImageService;
import com.jewelry.KiraJewelry.service.ProductionOrderService;
import com.jewelry.KiraJewelry.service.UserService;

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
        // Lấy thông tin người dùng từ session
        String customerId = (String) session.getAttribute("customerId");

        // Tạo production_Order_Id
        String productionOrderId = generateProductionOrderId();

        // Tạo ngày hiện tại
        Date currentDate = new Date();

        // Tạo đối tượng ProductionOrder từ dữ liệu nhận được từ form
        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setProduction_Order_Id(productionOrderId);
        productionOrder.setDate(currentDate);
        productionOrder.setCustomer_Id(customerId);
        productionOrder.setCategory_Id(categoryId);
        productionOrder.setProduct_Size(productSize);
        productionOrder.setDescription(description);
        productionOrder.setStatus("Created");

        // Đặt các thuộc tính còn lại là null hoặc 0
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

        // Lưu đối tượng ProductionOrder vào cơ sở dữ liệu
        productionOrderRepository.save(productionOrder);

        // Redirect đến trang thành công
        return "success";
    }

    private String generateProductionOrderId() {
        // Lấy production_Order_Id cuối cùng đã được chèn vào
        Optional<ProductionOrder> lastOrder = productionOrderService.getTopByOrderByProduction_Order_IdDesc();
        if (lastOrder.isPresent()) {
            String lastId = lastOrder.get().getProduction_Order_Id();
            // Trích xuất phần số và tăng lên
            int numericPart = Integer.parseInt(lastId.substring(3)) + 1;
            // Định dạng lại ID mới theo mẫu mong muốn
            return String.format("POI%03d", numericPart);
        } else {
            // Nếu không có order nào trước đó, bắt đầu với POI001
            return "POI001";
        }
    }

    @Autowired
    FilesStorageService storageService;

    @Autowired
    ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    // @PostMapping("/submitRequest")
    // public String submitRequest(
    // @RequestParam("category") int categoryId,
    // @RequestParam("productSize") int productSize,
    // @RequestParam("material_Name") String materialName,
    // @RequestParam("material_Color") String materialColor,
    // @RequestParam("material_Weight") float materialWeight,
    // @RequestParam("gem_Name") String gemName,
    // @RequestParam("gem_Color") String gemColor,
    // @RequestParam("gem_Weight") float gemWeight,
    // @RequestParam("description") String description,
    // @RequestParam("file") MultipartFile img_url, //
    // ****************************** */
    // HttpSession session, Model model) {

    // // Lấy thông tin người dùng từ session
    // String customerId = (String) session.getAttribute("customerId");

    // // Tạo production_Order_Id theo POI00001, POI00002, ...
    // // Đếm số lượng đơn hàng hiện tại
    // long currentOrderCount = productionOrderRepository.count();

    // String productionOrderId = "POI" + String.format("%05d", currentOrderCount +
    // 1);

    // // Tạo ngày hiện tại
    // Date currentDate = new Date();

    // // Tạo đối tượng ProductionOrder từ dữ liệu nhận được từ form
    // ProductionOrder productionOrder = new ProductionOrder();
    // // ****************************** */
    // String message = "";

    // try {
    // String imgdb = imageService.upload(img_url);
    // productionOrder.setImage_url(imgdb);
    // System.out.println(imgdb);
    // storageService.save(img_url); // Save in Storage provided by 3rd-party
    // message = "Uploaded the image successfully: " +
    // img_url.getOriginalFilename();
    // model.addAttribute("message", message);
    // } catch (Exception e) {
    // message = "Could not upload the image: " + img_url.getOriginalFilename() + ".
    // Error: " + e.getMessage();
    // model.addAttribute("message", message);
    // }
    // // ****************************** */

    // productionOrder.setProduction_Order_Id(productionOrderId);
    // productionOrder.setDate(currentDate);
    // productionOrder.setCustomer_Id(customerId);
    // productionOrder.setCategory_Id(categoryId);
    // productionOrder.setMaterial_Name(materialName);
    // productionOrder.setMaterial_Color(materialColor);
    // productionOrder.setMaterial_Weight(materialWeight);
    // productionOrder.setGem_Name(gemName);
    // productionOrder.setGem_Color(gemColor);
    // productionOrder.setGem_Weight(gemWeight);
    // productionOrder.setProduct_Size(productSize);
    // productionOrder.setDescription(description);

    // productionOrder.setStatus("Created");

    // // Đặt các thuộc tính còn lại là null hoặc 0
    // productionOrder.setDiamond_Amount(0);
    // productionOrder.setMaterial_Amount(0);
    // productionOrder.setProduction_Amount(0);
    // productionOrder.setSide_Gem_Cost(0);
    // productionOrder.setTotal_Amount(0);
    // productionOrder.setSales_Staff_Name(null);
    // productionOrder.setDesign_Staff_Name(null);
    // productionOrder.setProduction_Staff_Name(null);
    // productionOrder.setProduct(null);

    // // Lưu đối tượng ProductionOrder vào cơ sở dữ liệu
    // productionOrderRepository.save(productionOrder);

    // List<User> users = userService.getUsersByRoleId(4);
    // List<Employee> employees = new ArrayList<>();
    // for (User user : users) {
    // int userId = user.getUser_Id();
    // Employee employee = employeeService.getEmployeeByUserId(userId);
    // if (employee != null) {
    // employees.add(employee);
    // }
    // }

    // String customerName = (String) session.getAttribute("customerName");
    // Customer customer =
    // customerService.getCustomerIdByCustomerName(customerName);

    // model.addAttribute("customer", customer);
    // String catergoryName =
    // categoryService.getCateNameById(productionOrder.getCategory_Id());
    // model.addAttribute("categoryName", catergoryName);
    // model.addAttribute("productionOrder", productionOrder);
    // model.addAttribute("employees", employees);

    // return "customer/userRequest";

    // // Redirect đến trang thành công
    // // return "success";
    // }

}
