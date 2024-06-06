package com.KiraJewelry.KiraJewelry.controller;

import com.KiraJewelry.KiraJewelry.DTO.CustomerDTO;
import com.KiraJewelry.KiraJewelry.DTO.RegisterForm;
import com.KiraJewelry.KiraJewelry.DTO.UserDTO;
import com.KiraJewelry.KiraJewelry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
public class RegisterController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public String register(Model model) {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setUser(new UserDTO());
        registerForm.setCustomer(new CustomerDTO());
        model.addAttribute("registerForm", registerForm);
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("registerForm") RegisterForm registerForm) {
        System.out.println(registerForm.getUser());
        registerForm.getUser().setRoleId(1);
        registerForm.getUser().setStatus(1);
        System.out.println(registerForm.getCustomer());

        // Generate customerId in the correct format, e.g., "CUS001"
        String customerId = generateCustomerId();
        registerForm.getCustomer().setCustomerId(customerId);

        registerForm.getCustomer().setUser(registerForm.getUser());
        service.registerUserAndCustomer(registerForm.getUser(), registerForm.getCustomer());
        return "home";
    }

    private String generateCustomerId() {
        // Implement a method to generate customerId in the required format
        // For example, here we're using a UUID and extracting a part of it
        return "CUS" + UUID.randomUUID().toString().substring(0, 3);
    }
}
