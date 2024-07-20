package com.jewelry.KiraJewelry.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EducationController {
    @GetMapping("/education")
    public String education() {
        return "Education";
    }
}
