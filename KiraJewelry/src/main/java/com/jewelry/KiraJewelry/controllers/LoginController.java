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
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,
            HttpSession session) {

        User user = userService.login(email, password);

        if (user != null) {
            Employee employee;
            Customer customer;

            if (user.getRole_Id() == 1) {
                customer = customerService.getCustomerByUserId(user.getUser_Id());
                session.setAttribute("customerName", customer.getFull_Name());
                session.setAttribute("customerPN", customer.getPhoneNumber());
                session.setAttribute("customerId", customer.getCustomer_Id());
                return "home";

            } else {
                employee = employeeService.getEmployeeByUserId(user.getUser_Id());
                session.setAttribute("employeeName", employee.getFull_Name());
                if (user.getRole_Id() == 3) {
                    return "employee/manager/home";
                } else if (user.getRole_Id() == 4) {
                    return "employee/sales_staff/home";
                } else if (user.getRole_Id() == 5) {
                    return "employee/design_staff/home";
                } else if (user.getRole_Id() == 6) {
                    return "employee/production_staff/home";
                }

            }
        }
        return "error";
    }
}
