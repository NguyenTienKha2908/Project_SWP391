package com.jewelry.KiraJewelry.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate(); 
        return "redirect:/"; 
    }
}