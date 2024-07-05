package com.jewelry.KiraJewelry.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.User;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;

    // @PostMapping("/loginNew")
    // public String login(@RequestParam("email") String email,
    // @RequestParam("password") String password,
    // HttpSession session) {

    // User user = userService.login(email, password);

    // if (user != null) {
    // Employee employee;
    // Customer customer;

    // if (user.getRole_Id() == 1) {
    // customer = customerService.getCustomerByUserId(user.getUser_Id());
    // session.setAttribute("customerName", customer.getFull_Name());
    // session.setAttribute("customerPN", customer.getPhoneNumber());
    // session.setAttribute("customerId", customer.getCustomer_Id());
    // session.setAttribute("customerAddress", customer.getAddress());
    // session.setAttribute("customerEmail", user.getEmail());
    // return "redirect:/";

    // } else {
    // employee = employeeService.getEmployeeByUserId(user.getUser_Id());
    // session.setAttribute("employeeId", employee.getEmployee_Id());
    // session.setAttribute("employeeName", employee.getFull_Name());
    // session.setAttribute("employeeEmail", user.getEmail());
    // session.setAttribute("employeePassword", user.getPassword());
    // if (user.getRole_Id() == 3) {
    // session.setAttribute("role", "Manager");
    // return "redirect:/homeManager";
    // } else if (user.getRole_Id() == 4) {
    // session.setAttribute("role", "Sales Staff");
    // return "redirect:/homeSalesStaff";
    // } else if (user.getRole_Id() == 5) {
    // session.setAttribute("role", "Desgin Staff");
    // return "redirect:/homeDesginStaff";
    // } else if (user.getRole_Id() == 6) {
    // session.setAttribute("role", "Production Staff");
    // return "redirect:/homeProductionStaff";
    // }

    // }
    // }
    // return "error";
    // }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        // var authourities = authentication.getAuthorities();

        // Optional<String> role = authourities.stream()
        // .map(r -> r.getAuthority())
        // .findFirst();

        // if (role.isPresent()) {
        // switch (role.get()) {
        // case "1":
        // response.sendRedirect("/");
        // break;
        // case "2":
        // response.sendRedirect("/admin-page");
        // break;
        // case "3":
        // response.sendRedirect("/sale-page");
        // break;
        // case "4":
        // response.sendRedirect("/manager-page");
        // break;
        // case "5":
        // response.sendRedirect("/design-page");
        // break;
        // case "6":
        // response.sendRedirect("/product-page");
        // break;
        // default:
        // response.sendRedirect("/home-page");
        // break;
        // }
        // } else {
        // response.sendRedirect("/");
        // }
        // }
        if (user != null) {
            if (user.getRole_Id() == 1) {
                Customer customer = customerService.getCustomerByUserId(user.getUser_Id());
                session.setAttribute("customerName", customer.getFull_Name());
                session.setAttribute("customerPN", customer.getPhoneNumber());
                session.setAttribute("customerId", customer.getCustomer_Id());
                session.setAttribute("customerAddress", customer.getAddress());
                session.setAttribute("customerEmail", user.getEmail());
                response.sendRedirect("/login?success");
                return;
            } else {
                Employee employee = employeeService.getEmployeeByUserId(user.getUser_Id());
                session.setAttribute("employeeId", employee.getEmployee_Id());
                session.setAttribute("employeeName", employee.getFull_Name());
                session.setAttribute("employeeEmail", user.getEmail());
                session.setAttribute("employeePassword", user.getPassword());
                switch (user.getRole_Id()) {
                    case 3:
                        session.setAttribute("role", "Manager");
                        response.sendRedirect("/homeManager");
                        return;
                    case 4:
                        session.setAttribute("role", "Sales Staff");
                        response.sendRedirect("/homeSalesStaff");
                        return;
                    case 5:
                        session.setAttribute("role", "Design Staff");
                        response.sendRedirect("/homeDesignStaff");
                        return;
                    case 6:
                        session.setAttribute("role", "Production Staff");
                        response.sendRedirect("/homeProductionStaff");
                        return;
                    default:
                        response.sendRedirect("/login?success");
                        break;
                }
            }
        } else {
            response.sendRedirect("/login?error");
        }
        response.sendRedirect("/");
    }
}
