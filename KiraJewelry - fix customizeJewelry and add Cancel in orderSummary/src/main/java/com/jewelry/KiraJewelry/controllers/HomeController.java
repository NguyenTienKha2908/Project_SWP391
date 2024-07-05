package com.jewelry.KiraJewelry.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/homeCustomer")
    public String homeCustomer() {
        return "customer/home";
    }

    @GetMapping("/homeManager")
    public String homeManager() {
        return "employee/manager/profile";
    }

    @GetMapping("/homeSalesStaff")
    public String homeSalesStaff() {
        return "employee/sales_staff/profile";
    }

    @GetMapping("/homeProductionStaff")
    public String homeProductionStaff() {
        return "employee/production_staff/profile";
    }

    @GetMapping("/homeDesignStaff")
    public String homeDesignStaff() {
        return "employee/design_staff/profile";
    }

}