package com.jewelry.KiraJewelry.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}