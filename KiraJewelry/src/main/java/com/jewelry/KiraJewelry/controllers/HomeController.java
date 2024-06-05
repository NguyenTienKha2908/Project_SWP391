package com.jewelry.KiraJewelry.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/homeManager")
    public String homeManager() {
        return "employee/manager/home";
    }

    @GetMapping("/homeSalesStaff")
    public String homeSalesStaff() {
        return "employee/sales_staff/home";
    }

    @GetMapping("/homeProductionStaff")
    public String homeProductionStaff() {
        return "employee/production_staff/home";
    }

    @GetMapping("/homeDesignStaff")
    public String homeDesignStaff() {
        return "employee/design_staff/home";
    }
}
