package com.example.demo.controller;

import com.example.demo.entity.Users;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/customers")
    public String viewCustomersPage(Model model, @ModelAttribute("message") String message) {
        List<Users> customers = usersService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 1) // Assuming 1 is for customers
                .toList();
        model.addAttribute("customers", customers);
        model.addAttribute("message", message);
        return "Users/manage_customer";
    }

    @GetMapping("/staff")
    public String viewStaffPage(Model model, @ModelAttribute("message") String message) {
        List<Users> admins = usersService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 2) // Assuming 2 is for admins
                .toList();
        List<Users> managers = usersService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 3) // Assuming 3 is for managers
                .toList();
        List<Users> salesStaff = usersService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 4) // Assuming 4 is for sales staff
                .toList();
        List<Users> productionStaff = usersService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 5) // Assuming 5 is for production staff
                .toList();
        List<Users> designStaff = usersService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 6) // Assuming 6 is for design staff
                .toList();
        model.addAttribute("admins", admins);
        model.addAttribute("managers", managers);
        model.addAttribute("salesStaff", salesStaff);
        model.addAttribute("productionStaff", productionStaff);
        model.addAttribute("designStaff", designStaff);
        model.addAttribute("newStaff", new Users()); // For adding new staff
        model.addAttribute("message", message);
        return "Users/manage_staff";
    }

    @GetMapping("/add_staff")
    public String addStaffPage(Model model) {
        model.addAttribute("newStaff", new Users());
        return "Users/add_staff";
    }

    @PostMapping("/users/addStaff")
    public String addStaff(@ModelAttribute("newStaff") @Valid Users newStaff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Users/add_staff";
        }
        usersService.saveUsers(newStaff);
        return "redirect:/staff";
    }

    @PostMapping("/users/activateCustomer/{id}")
    public String activateCustomer(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            Users user = usersService.getUsersById(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("message", "User not found.");
            } else {
                user.setStatus(true); // Set status to active
                usersService.saveUsers(user);
                redirectAttributes.addFlashAttribute("message", "Customer activated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during activation: " + e.getMessage());
        }
        return "redirect:/customers";
    }

    @PostMapping("/users/deactivateCustomer/{id}")
    public String deactivateCustomer(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            Users user = usersService.getUsersById(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("message", "User not found.");
            } else {
                user.setStatus(false); // Set status to inactive
                usersService.saveUsers(user);
                redirectAttributes.addFlashAttribute("message", "Customer deactivated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during deactivation: " + e.getMessage());
        }
        return "redirect:/customers";
    }

    @PostMapping("/users/activateStaff/{id}")
    public String activateStaff(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            Users user = usersService.getUsersById(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("message", "User not found.");
            } else {
                user.setStatus(true); // Set status to active
                usersService.saveUsers(user);
                redirectAttributes.addFlashAttribute("message", "Staff activated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during activation: " + e.getMessage());
        }
        return "redirect:/staff";
    }

    @PostMapping("/users/deactivateStaff/{id}")
    public String deactivateStaff(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            Users user = usersService.getUsersById(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("message", "User not found.");
            } else {
                user.setStatus(false); // Set status to inactive
                usersService.saveUsers(user);
                redirectAttributes.addFlashAttribute("message", "Staff deactivated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during deactivation: " + e.getMessage());
        }
        return "redirect:/staff";
    }

    @GetMapping("/manageUsers")
    public String manageUsersPage(Model model) {
        return "Users/manage_users";
    }
}
