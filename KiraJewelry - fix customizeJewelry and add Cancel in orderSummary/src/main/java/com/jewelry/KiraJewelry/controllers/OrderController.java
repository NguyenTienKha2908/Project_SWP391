package com.jewelry.KiraJewelry.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Diamond;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.Material;
import com.jewelry.KiraJewelry.models.Product;
import com.jewelry.KiraJewelry.models.ProductMaterial;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.service.CategoryService;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.EmployeeService;
import com.jewelry.KiraJewelry.service.ImageService;
import com.jewelry.KiraJewelry.service.MaterialService;
import com.jewelry.KiraJewelry.service.ProductMaterialService;
import com.jewelry.KiraJewelry.service.ProductService;
import com.jewelry.KiraJewelry.service.ProductionOrderService;
import com.jewelry.KiraJewelry.service.Diamond.DiamondService;

@Controller
public class OrderController {

    @GetMapping("/currentOrder")
    public String currentOrder() {
        return "customer/currentOrder";
    }

    @GetMapping("/userPage")
    public String userPage() {
        return "customer/userPage";
    }

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private ProductMaterialService productMaterialService;

    @Autowired
    private ProductionOrderService productionOrderService;

    // @Autowired
    // private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    ImageService imageService;

    @GetMapping("/userRequests")
    public String userRequests(@RequestParam("customerName") String customerName, Model model) {
        Customer customer = customerService.getCustomerIdByCustomerName(customerName);
        String customerId = customer.getCustomer_Id();

        List<ProductionOrder> createdOrders = productionOrderService.getProductionOrderByStatus("Created");
        List<ProductionOrder> requestedOrders = productionOrderService.getProductionOrderByStatus("Requested");

        List<ProductionOrder> allOrders = new ArrayList<>();
        allOrders.addAll(createdOrders);
        allOrders.addAll(requestedOrders);

        List<ProductionOrder> customerOrders = allOrders.stream()
                .filter(porder -> customerId.equalsIgnoreCase(porder.getCustomer().getCustomer_Id()))
                .collect(Collectors.toList());

        model.addAttribute("customerOrders", customerOrders);
        model.addAttribute("customer", customer);
        return "customer/allUserRequest";
    }

    @GetMapping("/userRequest")
    public String userViewRequest(@RequestParam("orderId") String orderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(orderId);
        if (productionOrder == null) {
            // Handle the case where the production order is not found
            return "error";
        }

        String customerId = productionOrder.getCustomer().getCustomer_Id();
        Customer customer = productionOrder.getCustomer();

        List<String> imagesByCustomerId = null;
        try {
            imagesByCustomerId = imageService.getImgByCustomerID(customerId, productionOrder.getProduction_Order_Id());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        model.addAttribute("imagesByCustomerId", imagesByCustomerId);
        model.addAttribute("customer", customer);
        model.addAttribute("productionOrder", productionOrder);

        Employee employee = employeeService.getEmployeeById(productionOrder.getSales_Staff());
        if (employee == null) {
            model.addAttribute("sale_staff_name", "Sales Staff will contact you soon");
        } else {
            model.addAttribute("sale_staff_name", employee.getFull_Name());
        }

        String categoryName = categoryService.getCateNameById(productionOrder.getCategory().getCategory_Id());
        model.addAttribute("categoryName", categoryName);

        return "customer/userRequest";
    }

    @GetMapping("/userQuote")
    public String userViewQuote(@RequestParam("customerName") String customerName, Model model) {
        List<ProductionOrder> productionList = productionOrderService.getAllProductionOrders();

        Customer customer = customerService.getCustomerIdByCustomerName(customerName);
        String customerId = customer.getCustomer_Id();
        for (ProductionOrder porder : productionList) {

            if (customerId.equalsIgnoreCase(porder.getCustomer().getCustomer_Id())
                    && porder.getStatus().equalsIgnoreCase("Quoted")) {
                model.addAttribute("customer", customer);
                model.addAttribute("productionOrder", porder);
                String catergoryName = categoryService.getCateNameById(porder.getCategory().getCategory_Id());
                model.addAttribute("categoryName", catergoryName);
                return "customer/userQuote";
            }
        }
        // model.addAttribute("productionOrder", porder);
        return "customer/currentOrder";
    }

    @GetMapping("/userOrders")
    public String userOrders(@RequestParam("customerName") String customerName, Model model) {
        Customer customer = customerService.getCustomerIdByCustomerName(customerName);
        String customerId = customer.getCustomer_Id();

        List<ProductionOrder> createdOrders = productionOrderService.getProductionOrderByStatus("Ordered");
        List<ProductionOrder> requestedOrders = productionOrderService.getProductionOrderByStatus("Ordered(NP)");

        List<ProductionOrder> confirmedOrders = productionOrderService.getProductionOrderByStatus("Confirmed");
        List<ProductionOrder> completeOrders = productionOrderService.getProductionOrderByStatus("Completed");

        List<ProductionOrder> allOrders = new ArrayList<>();
        allOrders.addAll(createdOrders);
        allOrders.addAll(requestedOrders);
        allOrders.addAll(confirmedOrders);
        allOrders.addAll(completeOrders);

        List<ProductionOrder> customerOrders = allOrders.stream()
                .filter(porder -> customerId.equalsIgnoreCase(porder.getCustomer().getCustomer_Id()))
                .collect(Collectors.toList());

        model.addAttribute("customerOrders", customerOrders);
        model.addAttribute("customer", customer);
        return "customer/allUserOrder";
    }

    @GetMapping("/userOrder")
    public String userViewOrder(@RequestParam("orderId") String orderId, Model model) throws IOException {
        Map<String, List<String>> imagesByStaffId = new HashMap<>();
        Map<String, List<String>> imagesByPRId = new HashMap<>();
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(orderId);
        Product product = productService.getProductById(productionOrder.getProduct().getProduct_Id());
        Employee employee = employeeService.getEmployeeById(productionOrder.getDesign_Staff());

        ProductMaterial productMaterial = productMaterialService
                .getProductMaterialByProduct_id(product.getProduct_Id());
        Diamond diamond = diamondService.getDiamondByProductId(product.getProduct_Id());
        Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());

        String cateUrl = imageService
                .getImgByCateogryID(String.valueOf(product.getCategory().getCategory_Id()));
        String materialUrl = imageService
                .getImgByMaterialID(String.valueOf(productMaterial.getId().getMaterial_Id()));
        String diamondUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));

        try {
            imagesByStaffId = imageService.getImgOrderedByStaffId(employee.getEmployee_Id());
            imagesByPRId = imageService.getImgOrderedByPRStaffId(productionOrder.getProduction_Staff());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String message = "";
        if (productionOrder.getStatus().equalsIgnoreCase("Ordered(NP)")) {
            message = "The design is rejected";
        } else if (productionOrder.getStatus().equalsIgnoreCase("Ordered")) {
            message = "The design is updated";

        }
        model.addAttribute("message", message);
        model.addAttribute("imagesByStaffId", imagesByStaffId);
        model.addAttribute("imagesByPRId", imagesByPRId);
        model.addAttribute("product", product);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("productMaterial", productMaterial);
        model.addAttribute("material", material);
        model.addAttribute("diamond", diamond);
        model.addAttribute("cateUrl", cateUrl);
        model.addAttribute("materialUrl", materialUrl);
        model.addAttribute("diamondUrl", diamondUrl);

        return "customer/userOrder";
    }

    @GetMapping("/userCustomizedOrders")
    public String userCustomizedOrders(@RequestParam("customerName") String customerName, Model model) {
        Customer customer = customerService.getCustomerIdByCustomerName(customerName);
        String customerId = customer.getCustomer_Id();

        List<ProductionOrder> customizedOrders = productionOrderService.getProductionOrderByStatus("Customized");

        List<ProductionOrder> customerOrders = customizedOrders.stream()
                .filter(porder -> customerId.equalsIgnoreCase(porder.getCustomer().getCustomer_Id()))
                .collect(Collectors.toList());

        model.addAttribute("customerOrders", customerOrders);
        model.addAttribute("customer", customer);
        return "customer/customizeJewelry/allUserCustomizedOrders";
    }

    // @GetMapping("/userCustomizedOrder")
    // public String userViewCustomizedOrder(@RequestParam("customerName") String
    // customerName, Model model)
    // throws IOException {
    // List<ProductionOrder> productionList =
    // productionOrderService.getAllProductionOrders();

    // Customer customer =
    // customerService.getCustomerIdByCustomerName(customerName);
    // String customerId = customer.getCustomer_Id();

    // for (ProductionOrder porder : productionList) {

    // if (customerId.equalsIgnoreCase(porder.getCustomer().getCustomer_Id())
    // && (porder.getStatus().equalsIgnoreCase("Customized"))) {
    // model.addAttribute("customer", customer);
    // model.addAttribute("productionOrder", porder);
    // ProductMaterial productMaterial = productMaterialService
    // .getProductMaterialByProduct_id(porder.getProduct().getProduct_Id());
    // Diamond diamond =
    // diamondService.getDiamondByProductId(porder.getProduct().getProduct_Id());
    // Material material =
    // materialService.getMaterialById(productMaterial.getId().getMaterial_Id());
    // String cateUrl =
    // imageService.getImgByCateogryID(String.valueOf(porder.getCategory().getCategory_Id()));
    // String materialUrl = imageService
    // .getImgByMaterialID(String.valueOf(productMaterial.getId().getMaterial_Id()));
    // String diamondUrl =
    // imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));
    // model.addAttribute("diamond", diamond);
    // model.addAttribute("productMaterial", productMaterial);
    // model.addAttribute("material", material);
    // model.addAttribute("cateUrl", cateUrl);
    // model.addAttribute("materialUrl", materialUrl);
    // model.addAttribute("diamondUrl", diamondUrl);

    // return "customer/customizeJewelry/orderSummary";
    // }
    // }

    // return "customer/currentOrder";
    // }

    @GetMapping("/userCustomizedOrder")
    public String userViewCustomizedOrder(@RequestParam("orderId") String orderId, Model model) throws IOException {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(orderId);
        if (productionOrder == null) {
            // Handle the case where the production order is not found
            return "error";
        }

        String customerName = productionOrder.getCustomer().getFull_Name();
        Customer customer = customerService.getCustomerIdByCustomerName(customerName);

        ProductMaterial productMaterial = productMaterialService
                .getProductMaterialByProduct_id(productionOrder.getProduct().getProduct_Id());
        Diamond diamond = diamondService.getDiamondByProductId(productionOrder.getProduct().getProduct_Id());
        Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());
        Product product = productService.getProductById(productMaterial.getId().getProduct_Id());
        String cateUrl = imageService
                .getImgByCateogryID(String.valueOf(productionOrder.getCategory().getCategory_Id()));
        String materialUrl = imageService
                .getImgByMaterialID(String.valueOf(productMaterial.getId().getMaterial_Id()));
        String diamondUrl = imageService.getImgByDiamondID(String.valueOf(diamond.getDia_Id()));

        model.addAttribute("customer", customer);
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("diamond", diamond);
        model.addAttribute("product", product);
        model.addAttribute("productMaterial", productMaterial);
        model.addAttribute("material", material);
        model.addAttribute("cateUrl", cateUrl);
        model.addAttribute("materialUrl", materialUrl);
        model.addAttribute("diamondUrl", diamondUrl);

        return "customer/customizeJewelry/orderSummary";
    }

    @PostMapping("/userAcceptQuote")
    public String acceptUserQuote(@RequestParam("productionOrderId") String productionOrderId) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        productionOrder.setStatus("Order(NP)");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder";
    }

}
