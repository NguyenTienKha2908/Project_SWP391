package com.jewelry.KiraJewelry.controllers;

import java.io.IOException;
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
import com.jewelry.KiraJewelry.models.Product;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.repository.ProductRepository;
import com.jewelry.KiraJewelry.repository.ProductionOrderRepository;
import com.jewelry.KiraJewelry.service.CategoryService;
import com.jewelry.KiraJewelry.service.CustomerService;
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

    @Autowired
    FilesStorageService storageService;

    @Autowired
    ImageService imageService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductRepository productRepository;


    @PostMapping("/submitRequest")
    public String submitRequest(@RequestParam("category") int categoryId,
            @RequestParam("productSize") int productSize,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            HttpSession session, Model model) {

        // Get user info from session
        String customerId = (String) session.getAttribute("customerId");
        Customer customer = customerService.getCustomerByCustomerId(customerId);

        // Generate production_Order_Id
        String productionOrderId = generateProductionOrderId();

        // Create current date
        Date currentDate = new Date();

        // Create ProductionOrder object from form data
        ProductionOrder productionOrder = new ProductionOrder();

        productionOrder.setProduction_Order_Id(productionOrderId);
        productionOrder.setDate(currentDate);
        productionOrder.setCustomer(customer);
        productionOrder.setCategory(categoryService.getCategoryById(categoryId));
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
        productionOrder.setSales_Staff(null);
        productionOrder.setDesign_Staff(null);
        productionOrder.setProduction_Staff(null);
       

        // Create and save Product entity
        Product product = new Product();
        product.setProduct_Name("Product for " + productionOrderId); // Set an appropriate product name
        product.setProduct_Code(generateProductCode(productionOrderId));
        product.setCategory(categoryService.getCategoryById(categoryId));
        product.setDescription(description);
        product.setSize(productSize);
        product.setStatus(true); // Set status as needed
        product.setCollection(null);
        product.setGender("no gender");
        product.setStatus(false);

        System.out.println(productionOrder.getProduction_Order_Id());
        // Save ProductionOrder to database

        try {
            String url = imageService.uploadForProductionOrder(file, "Customer_Production_Order", customerId,
                    productionOrder.getProduction_Order_Id());
            productRepository.save(product);
            productionOrder.setProduct(product);
            // productionOrder.setImg_Url(url);
        } catch (Exception e) {
        }

        try {
            productionOrderRepository.save(productionOrder);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception (log it, show error message, etc.)
            return "redirect:/request?error";
        }

        List<String> imagesByCustomerId = null;

        try {
            imagesByCustomerId = imageService.getImgByCustomerID(customerId, productionOrder.getProduction_Order_Id());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        model.addAttribute("imagesByCustomerId", imagesByCustomerId);
        model.addAttribute("customer", customer);
        model.addAttribute("productionOrder", productionOrder);
        String catergoryName = categoryService.getCateNameById(productionOrder.getCategory().getCategory_Id());
        model.addAttribute("categoryName", catergoryName);

        // Redirect to success page
        // return "customer/userRequest";
        return "redirect:/request?success";
    }

    private String generateProductionOrderId() {
        // Lấy production_Order_Id cuối cùng đã được chèn vào
        ProductionOrder lastOrder = productionOrderService.getTopByOrderByProduction_Order_IdDesc();
        if (lastOrder != null) {
            if (lastOrder.getProduction_Order_Id() != null) {
                String lastId = lastOrder.getProduction_Order_Id();
                // Trích xuất phần số và tăng lên
                int numericPart = Integer.parseInt(lastId.substring(3)) + 1;
                // Định dạng lại ID mới theo mẫu mong muốn
                return String.format("POI%03d", numericPart);
            } else {
                // Nếu không có order nào trước đó, bắt đầu với POI001
                return "POI001";
            }
        } else {
            return "POI001";
        }
    }

    private String generateProductCode(String productionOrderId) {
        // Remove the first character and replace 'O' with ''
        return "PO00" + productionOrderId.substring(3);
    }

    
}
