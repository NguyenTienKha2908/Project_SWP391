package com.jewelry.KiraJewelry.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class EducationController {
    @GetMapping("/education")
    public String education(Model model, HttpServletRequest request) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "Education";
    }
}
