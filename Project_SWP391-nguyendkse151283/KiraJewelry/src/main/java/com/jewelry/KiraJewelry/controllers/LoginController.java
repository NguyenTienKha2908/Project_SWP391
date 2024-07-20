package com.jewelry.KiraJewelry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.EmployeeService;
import com.jewelry.KiraJewelry.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,
                        HttpSession session) {

        User user = userService.login(email, password);

        if (user != null) {
            if (user.getRoleId() == 1) {
                Customer customer = customerService.getCustomerByUserId(user.getUserId());
                session.setAttribute("customerName", customer.getFullName());
                session.setAttribute("customerPN", customer.getPhoneNumber());
                session.setAttribute("customerId", customer.getCustomerId());
                return "customer/home"; 
            } else {
                Employee employee = employeeService.getEmployeeByUserId(user.getUserId());
                session.setAttribute("employeeName", employee.getFullName());
                if (user.getRoleId() == 3) {
                    return "employee/manager/home";
                } else if (user.getRoleId() == 4) {
                    return "employee/sales_staff/home";
                } else if (user.getRoleId() == 5) {
                    return "employee/design_staff/home";
                } else if (user.getRoleId() == 6) {
                    return "employee/production_staff/home";
                }
            }
        }
        return "error";
    }
}
