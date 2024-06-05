package com.jewelry.KiraJewelry.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.EmployeeService;
import com.jewelry.KiraJewelry.service.ProductionOrderService;
import com.jewelry.KiraJewelry.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductionOrderController {

    @Autowired
    private ProductionOrderService productionOrderService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/listProductionOrders")
    public String getAllProductionOrders(Model model) {
        List<ProductionOrder> productionOrders = productionOrderService.getAllProductionOrders();
        List<User> users = userService.getUsersByRoleId(4);
        List<Employee> employees = new ArrayList<>();
        for (User user : users) {
            int userId = user.getUser_Id();
            Employee employee = employeeService.getEmployeeByUserId(userId);
            if (employee != null) {
                employees.add(employee);
            }
        }
        model.addAttribute("listProductionOrders", productionOrders);
        model.addAttribute("employees", employees);
        return "employee/manager/list";
    }

    @GetMapping("/viewProductionOrders")
    public String viewProductionOrder(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        List<User> users = userService.getUsersByRoleId(4);
        List<Employee> employees = new ArrayList<>();
        for (User user : users) {
            int userId = user.getUser_Id();
            Employee employee = employeeService.getEmployeeByUserId(userId);
            if (employee != null) {
                employees.add(employee);
            }
        }
        model.addAttribute("listRequests", productionOrder);
        model.addAttribute("employees", employees);
        return "employee/manager/viewRequest";
    }

    @PostMapping("/saveProductionOrder")
    public String saveProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
            @RequestParam(value = "staff", required = false) String employeeId,
            Model model) {
        // Get the ProductionOrder object by id
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        if (employeeId != null && !employeeId.isEmpty()) {
            // Update the sales staff information for ProductionOrder
            Employee employee = employeeService.getEmployeeById(employeeId);
            productionOrder.setSales_Staff_Name(employee.getFull_Name());
            productionOrder.setStatus("Request");
        }

        // Save the updated ProductionOrder
        productionOrderService.saveProductionOrder(productionOrder);

        // Redirect to the list page
        // return "employee/manager/list";
        return "redirect:/listProductionOrders";
    }

    @GetMapping("/viewRequestsforSS")
    public String getAllRequests(Model model, HttpSession session) {
        String employeeName = (String) session.getAttribute("employeeName");
        List<ProductionOrder> productionOrders = productionOrderService.getProductionOrderByStatusAndName("Request",
                employeeName);

        model.addAttribute("listRequests", productionOrders);
        return "employee/sales_staff/list";
    }

    @GetMapping("/viewInformationRequest")
    public String getRequests(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        model.addAttribute("listRequests", productionOrder);
        return "employee/sales_staff/viewRequest";
    }

    @GetMapping("/viewQuotesforSS")
    public String getQuotes(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Quote");
        productionOrderService.saveProductionOrder(productionOrder);
        return "employee/sales_staff/viewQuote";
    }

    @GetMapping("/confirmDeleteProductionOrder")
    public String confirmDeleteProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
            Model model) {
        model.addAttribute("productionOrderId", productionOrderId);
        return "employee/manager/delete";
    }

    @PostMapping("/deleteProductionOrder")
    public String deleteProductionOrder(@RequestParam("productionOrderId") String productionOrderId) {
        productionOrderService.deleteProductionOrderById(productionOrderId);

        return "redirect:/listProductionOrders";
    }

    @GetMapping("/viewEditPage")
    public String viewEditPage(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        model.addAttribute("productionOrder", productionOrder);
        return "employee/sales_staff/edit";
    }

    @PostMapping("/saveEditedProductionOrder")
    public String saveEditedProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
            @RequestParam("materialName") String materialName,
            @RequestParam("materialColor") String materialColor,
            @RequestParam("materialWeight") double materialWeight,
            @RequestParam("gemName") String gemName,
            @RequestParam("gemColor") String gemColor,
            @RequestParam("gemWeight") double gemWeight,
            @RequestParam("productSize") int productSize,
            Model model) {

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        productionOrder.setMaterial_Name(materialName);
        productionOrder.setMaterial_Color(materialColor);
        productionOrder.setMaterial_Weight(materialWeight);
        productionOrder.setGem_Name(gemName);
        productionOrder.setGem_Color(gemColor);
        productionOrder.setGem_Weight(gemWeight);
        productionOrder.setProduct_Size(productSize);

        productionOrderService.saveProductionOrder(productionOrder);
        model.addAttribute("listRequests", productionOrderService.getAllProductionOrders());

        return "redirect:/viewRequestsforSS";
    }

    @GetMapping("/prepareQuotePage")
    public String prepareQuotePage(@RequestParam("productionOrderId") String productionOrderId, Model model) {

        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        Customer customer = customerService.getCustomerByCustomerId(productionOrder.getCustomer_Id());
        model.addAttribute("productionOrder", productionOrder);
        model.addAttribute("customer", customer);
        return "employee/sales_staff/prepareQuotePage";
    }

}
