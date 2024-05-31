package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.models.User;
import com.example.demo.service.CustomerService;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.UserService;

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
            String customerName, employeeName;

            if (user.getRoleId() == 1) {
                customerName = customerService.getCustomerNameByUserId(user.getUser_Id());
                session.setAttribute("customerName", customerName);
                return "home";

            } else {
                employeeName = employeeService.getEmployeeNameByUserId(user.getUser_Id());
                session.setAttribute("employeeName", employeeName);
                return "staff/index";
            }
        }
        return "error";
    }
}
