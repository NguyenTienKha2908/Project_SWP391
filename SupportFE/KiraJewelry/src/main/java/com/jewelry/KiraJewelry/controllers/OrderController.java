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

import jakarta.servlet.http.HttpSession;

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
    public String userRequests(HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        Customer customer = customerService.getCustomerByCustomerId(customerId);

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

    @GetMapping("/userQuotes")
    public String viewProductionOrder(HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        if (customerId != null) {
            List<ProductionOrder> quoteList1 = productionOrderService.getProductionOrderByStatus("Quoted(WC)");
            List<ProductionOrder> quoteList2 = productionOrderService
                    .getProductionOrderByStatus("Quoted(CRJ)");

            List<ProductionOrder> allOrders = new ArrayList<>();
            allOrders.addAll(quoteList1);
            allOrders.addAll(quoteList2);

            List<ProductionOrder> customerOrders = allOrders.stream()
                    .filter(porder -> customerId.equalsIgnoreCase(porder.getCustomer().getCustomer_Id()))
                    .collect(Collectors.toList());

            model.addAttribute("customerOrders", customerOrders);
        }
        return "customer/allUserQuotes";
    }

    @GetMapping("/userOrders")
    public String userOrders(HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        Customer customer = customerService.getCustomerByCustomerId(customerId);

        List<ProductionOrder> Orders = productionOrderService.getProductionOrderByStatus("Ordered");
        List<ProductionOrder> WaitPayOrders = productionOrderService.getProductionOrderByStatus("Ordered(WP)");
        List<ProductionOrder> NotPassOrders = productionOrderService.getProductionOrderByStatus("Ordered(NP)");

        List<ProductionOrder> confirmedOrders = productionOrderService.getProductionOrderByStatus("Confirmed");
        List<ProductionOrder> completeOrders = productionOrderService.getProductionOrderByStatus("Completed");
        List<ProductionOrder> inDeliverOrders = productionOrderService.getProductionOrderByStatus("Delivering");

        List<ProductionOrder> allOrders = new ArrayList<>();
        allOrders.addAll(Orders);
        allOrders.addAll(WaitPayOrders);
        allOrders.addAll(confirmedOrders);
        allOrders.addAll(completeOrders);
        allOrders.addAll(NotPassOrders);
        allOrders.addAll(inDeliverOrders);

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

        if (employee != null) {
            try {
                imagesByStaffId = imageService.getImgOrderedByStaffId(employee.getEmployee_Id());
                imagesByPRId = imageService.getImgOrderedByPRStaffId(productionOrder.getProduction_Staff());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
    public String userCustomizedOrders(HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        Customer customer = customerService.getCustomerByCustomerId(customerId);

        List<ProductionOrder> customizedOrders = productionOrderService.getProductionOrderByStatus("Customized");
        List<ProductionOrder> paymentOrders = productionOrderService.getProductionOrderByStatus("Payment In Confirm");
        List<ProductionOrder> deliveringOrders = productionOrderService.getProductionOrderByStatus("Delivering");

        List<ProductionOrder> allOrders = new ArrayList<>();
        allOrders.addAll(customizedOrders);
        allOrders.addAll(paymentOrders);
        allOrders.addAll(deliveringOrders);

        List<ProductionOrder> customerOrders = allOrders.stream()
                .filter(porder -> customerId.equalsIgnoreCase(porder.getCustomer().getCustomer_Id()))
                .collect(Collectors.toList());

        model.addAttribute("customerOrders", customerOrders);
        model.addAttribute("customer", customer);
        return "customer/customizeJewelry/allUserCustomizedOrders";
    }

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

    @GetMapping("/userHistoryOrders")
    public String userHistoryOrders(HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("customerId");
        Customer customer = customerService.getCustomerByCustomerId(customerId);

        List<ProductionOrder> customizedOrders = productionOrderService.getProductionOrderByStatus("Delivered");
        List<ProductionOrder> paymentOrders = productionOrderService.getProductionOrderByStatus("Canceled");

        List<ProductionOrder> allOrders = new ArrayList<>();
        allOrders.addAll(customizedOrders);
        allOrders.addAll(paymentOrders);

        List<ProductionOrder> customerOrders = allOrders.stream()
                .filter(porder -> customerId.equalsIgnoreCase(porder.getCustomer().getCustomer_Id()))
                .collect(Collectors.toList());

        List<ProductMaterial> proMaterialList = new ArrayList<>();
        List<String> imagesByCategory = new ArrayList<>();
        List<Diamond> diamonds = new ArrayList<>();
        List<Material> materials = new ArrayList<>();
        // Iterate through each material to get its image URL
        for (ProductionOrder order : customerOrders) {
            try {
                String imageUrl = imageService.getImgByCateogryID(String.valueOf(order.getCategory().getCategory_Id()));
                imagesByCategory.add(imageUrl);
                ProductMaterial productMaterial = productMaterialService
                        .getProductMaterialByProductId(order.getProduct().getProduct_Id());
                Diamond diamond = diamondService.getDiamondByProductId(productMaterial.getId().getProduct_Id());
                Material material = materialService.getMaterialById(productMaterial.getId().getMaterial_Id());
                System.out.println(imageUrl);
                diamonds.add(diamond);
                proMaterialList.add(productMaterial);
                materials.add(material);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        model.addAttribute("materials", materials);
        model.addAttribute("diamonds", diamonds);
        model.addAttribute("productMaterials", proMaterialList);
        model.addAttribute("imagesByCategory", imagesByCategory);
        model.addAttribute("customerOrders", customerOrders);
        model.addAttribute("customer", customer);
        return "customer/userHistoryOrders";
    }

    @PostMapping("/userAcceptQuote")
    public String acceptUserQuote(@RequestParam("productionOrderId") String productionOrderId) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        productionOrder.setStatus("Order(NP)");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder";
    }

}
