package com.jewelry.KiraJewelry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jewelry.KiraJewelry.exceptions.ResourceNotFoundException;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.ProductionOrder;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.service.EmployeeService;
import com.jewelry.KiraJewelry.service.ProductionOrderService;
import com.jewelry.KiraJewelry.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/production-orders")
public class ProductionOrderController {

    @Autowired
    private ProductionOrderService productionOrderService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String getAllProductionOrders(Model model) {
        List<ProductionOrder> productionOrders = productionOrderService.getAllProductionOrders();
        List<User> users = userService.getUsersByRoleId(4); // Assuming role_id 4 is for sales staff
        List<Employee> employees = new ArrayList<>();
        for (User user : users) {
            Employee employee = employeeService.getEmployeeByUserId(user.getUserId());
            if (employee != null) {
                employees.add(employee);
            }
        }
        model.addAttribute("listProductionOrders", productionOrders);
        model.addAttribute("employees", employees);
        return "employee/manager/list";
    }

    @GetMapping("/view")
    public String viewProductionOrder(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        if (productionOrder == null) {
            throw new ResourceNotFoundException("ProductionOrder", "id", productionOrderId);
        }
        List<User> users = userService.getUsersByRoleId(4);
        List<Employee> employees = new ArrayList<>();
        for (User user : users) {
            Employee employee = employeeService.getEmployeeByUserId(user.getUserId());
            if (employee != null) {
                employees.add(employee);
            }
        }
        model.addAttribute("listRequests", productionOrder);
        model.addAttribute("employees", employees);
        return "employee/manager/viewRequest";
    }

    @PostMapping("/save")
    public String saveProductionOrder(@RequestParam("productionOrderId") String productionOrderId,
            @RequestParam(value = "staff", required = false) String employeeId,
            Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        if (employeeId != null && !employeeId.isEmpty()) {
            Employee employee = employeeService.getEmployeeById(employeeId);
            productionOrder.setSalesStaffName(employee.getFullName());
            productionOrder.setStatus("Request");
        }

        productionOrderService.saveProductionOrder(productionOrder);

        return "redirect:/production-orders/list";
    }

    @GetMapping("/requests")
    public String getAllRequests(Model model, HttpSession session) {
        String employeeName = (String) session.getAttribute("employeeName");
        List<ProductionOrder> productionOrders = productionOrderService
                .getProductionOrderByStatusAndSalesStaffName("Request", employeeName);

        model.addAttribute("listRequests", productionOrders);
        return "employee/sales_staff/list";
    }

    @GetMapping("/information")
    public String getRequests(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);

        model.addAttribute("listRequests", productionOrder);
        return "employee/sales_staff/viewRequest";
    }

    @GetMapping("/quotes")
    public String getQuotes(@RequestParam("productionOrderId") String productionOrderId, Model model) {
        ProductionOrder productionOrder = productionOrderService.getProductionOrderById(productionOrderId);
        productionOrder.setStatus("Quote");
        productionOrderService.saveProductionOrder(productionOrder);
        return "employee/sales_staff/quote";
    }

    @GetMapping("/approve")
    public String approveProductionOrder(@RequestParam("productionOrderId") String productionOrderId) {
        productionOrderService.updateOrderStatus(productionOrderId, "approved");
        return "redirect:/production-orders/view?productionOrderId=" + productionOrderId;
    }

    @GetMapping("/reject")
    public String rejectProductionOrder(@RequestParam("productionOrderId") String productionOrderId) {
        productionOrderService.updateOrderStatus(productionOrderId, "rejected");
        return "redirect:/production-orders/view?productionOrderId=" + productionOrderId;
    }

    // RESTful APIs

    @GetMapping("/api/requested-orders")
    @ResponseBody
    public List<ProductionOrder> getRequestedOrders() {
        return productionOrderService.getProductionOrdersByStatus("requested");
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<?> updateProductionOrder(@PathVariable String id,
            @RequestBody ProductionOrder productionOrder) {
        productionOrderService.updateProductionOrder(id, productionOrder);
        return ResponseEntity.ok("Production order updated successfully.");
    }

    @GetMapping("/api/quoted")
    @ResponseBody
    public List<ProductionOrder> getQuotedOrders() {
        return productionOrderService.getProductionOrdersByStatus("quoted");
    }

    @PutMapping("/api/manager/{id}")
    @ResponseBody
    public ResponseEntity<?> managerAction(@PathVariable String id, @RequestBody String action) {
        productionOrderService.managerAction(id, action);
        return ResponseEntity.ok("Action processed successfully.");
    }

    @PutMapping("/api/customer/{id}")
    @ResponseBody
    public ResponseEntity<?> customerAction(@PathVariable String id, @RequestBody String action) {
        productionOrderService.customerAction(id, action);
        return ResponseEntity.ok("Action processed successfully.");
    }

    @PutMapping("/api/order/{id}/status")
    @ResponseBody
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @RequestBody String status) {
        productionOrderService.updateOrderStatus(id, status);
        return ResponseEntity.ok("Order status updated successfully.");
    }

    @PostMapping("/api/accept-order/{id}")
    @ResponseBody
    public ResponseEntity<?> acceptOrder(@PathVariable String id) {
        productionOrderService.acceptOrder(id);
        return ResponseEntity.ok("Order accepted successfully.");
    }

}
