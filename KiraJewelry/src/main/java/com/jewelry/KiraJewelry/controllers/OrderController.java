package com.jewelry.KiraJewelry.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.service.CategoryService;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.ProductionOrderService;

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

    // @Autowired
    // private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private ProductionOrderService productionOrderService;

    // @Autowired
    // private UserService userService;

    // @Autowired
    // private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/userRequest")
    public String userViewRequest(@RequestParam("customerName") String customerName, Model model) {
        List<ProductionOrder> productionList = productionOrderService.getAllProductionOrders();

        Customer customer = customerService.getCustomerIdByCustomerName(customerName);
        String customerId = customer.getCustomer_Id();
        for (ProductionOrder porder : productionList) {

            if (customerId.equalsIgnoreCase(porder.getCustomer_Id()) && (porder.getStatus().equalsIgnoreCase("Created")
                    || porder.getStatus().equalsIgnoreCase("Request"))) {
                model.addAttribute("customer", customer);
                model.addAttribute("productionOrder", porder);
                String catergoryName = categoryService.getCateNameById(porder.getCategory_id());
                model.addAttribute("categoryName", catergoryName);
                return "customer/userRequest";
            }
        }
        // model.addAttribute("productionOrder", porder);
        return "customer/userRequest";
    }

    @GetMapping("/userQuote")
    public String userViewQuote(@RequestParam("customerName") String customerName, Model model) {
        List<ProductionOrder> productionList = productionOrderService.getAllProductionOrders();

        Customer customer = customerService.getCustomerIdByCustomerName(customerName);
        String customerId = customer.getCustomer_Id();
        for (ProductionOrder porder : productionList) {

            if (customerId.equalsIgnoreCase(porder.getCustomer_Id()) && porder.getStatus().equalsIgnoreCase("Quoted")) {
                model.addAttribute("customer", customer);
                model.addAttribute("productionOrder", porder);
                String catergoryName = categoryService.getCateNameById(porder.getCategory_id());
                model.addAttribute("categoryName", catergoryName);
                return "customer/userQuote";
            }
        }
        // model.addAttribute("productionOrder", porder);
        return "customer/currentOrder";
    }

    @GetMapping("/userOrder")
    public String userViewOrder(@RequestParam("customerName") String customerName, Model model) {
        List<ProductionOrder> productionList = productionOrderService.getAllProductionOrders();

        Customer customer = customerService.getCustomerIdByCustomerName(customerName);
        String customerId = customer.getCustomer_Id();
        for (ProductionOrder porder : productionList) {

            if (customerId.equalsIgnoreCase(porder.getCustomer_Id()) && (porder.getStatus().equalsIgnoreCase("Ordered")
                    || porder.getStatus().equalsIgnoreCase("Order(NP)"))) {
                model.addAttribute("customer", customer);
                model.addAttribute("productionOrder", porder);
                String catergoryName = categoryService.getCateNameById(porder.getCategory_id());
                model.addAttribute("categoryName", catergoryName);
                return "customer/userOrder";
            }
        }
        // model.addAttribute("productionOrder", porder);
        return "customer/currentOrder";
    }

    @PostMapping("/userAcceptQuote")
    public String acceptUserQuote(@RequestParam("productionOrderId") String productionOrderId) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        productionOrder.setStatus("Order(NP)");

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/userOrder";
    }

}
