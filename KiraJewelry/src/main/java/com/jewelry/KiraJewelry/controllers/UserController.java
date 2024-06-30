package com.jewelry.KiraJewelry.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.jewelry.KiraJewelry.dto.LoginRequest;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/registration")
    public String getRegistrationPage(Model model, RedirectAttributes redirectAttributes) {
        LoginRequest registerDto = new LoginRequest();
        model.addAttribute("user", registerDto);
        // Check for flash attributes and add them to the model if they exist
        if (redirectAttributes.getFlashAttributes().containsKey("fullNameError")) {
            model.addAttribute("fullNameError", redirectAttributes.getAttribute("fullNameError"));
        }
        if (redirectAttributes.getFlashAttributes().containsKey("emailError")) {
            model.addAttribute("emailError", redirectAttributes.getAttribute("emailError"));
        }
        if (redirectAttributes.getFlashAttributes().containsKey("passwordError")) {
            model.addAttribute("passwordError", redirectAttributes.getAttribute("passwordError"));
        }
        if (redirectAttributes.getFlashAttributes().containsKey("phoneError")) {
            model.addAttribute("phoneError", redirectAttributes.getAttribute("phoneError"));
        }
        if (redirectAttributes.getFlashAttributes().containsKey("userexist")) {
            model.addAttribute("userexist", redirectAttributes.getAttribute("userexist"));
        }
        if (redirectAttributes.getFlashAttributes().containsKey("message")) {
            model.addAttribute("message", redirectAttributes.getAttribute("message"));
        }

        if (redirectAttributes.getFlashAttributes().containsKey("error"))
            model.addAttribute("error", redirectAttributes.getAttribute("error"));

        return "register";
    }

    @PostMapping("/registration")
    public String registerSave(@Valid @ModelAttribute("user") LoginRequest registerDto, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError err : bindingResult.getFieldErrors()) {
                errors.put(err.getField(), err.getDefaultMessage());
            }
            if (redirectAttributes.getFlashAttributes().containsKey("userexist")) {
                redirectAttributes.addFlashAttribute("userexist", redirectAttributes.getAttribute("userexist"));
            }
            redirectAttributes.addFlashAttribute("error", errors);
            return "redirect:/registration?error"; // Add this return statement to handle the case when there are
                                                   // binding errors
        }
        // Validate phone number length
        if (registerDto.getPhone().length() != 10) {
            redirectAttributes.addFlashAttribute("phoneError", "Phone number must be exactly 10 digits");
            return "redirect:/registration?error";
        }

        User user = userService.findByEmail(registerDto.getEmail());
        if (user != null) {
            // Add userexist as a flash attribute for redirection
            redirectAttributes.addFlashAttribute("userexist", user);
            return "redirect:/registration?error";
        } else {

            userService.saveUserAndCustomer(registerDto);
            // Add message as a flash attribute for redirection
            redirectAttributes.addFlashAttribute("message", "Registered Successfully!");
            return "redirect:/registration?success";
        }
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/customers")
    public String viewCustomersPage(Model model, @ModelAttribute("message") String message) {
        List<User> customers = userService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 1) // Assuming 1 is for customers
                .toList();
        model.addAttribute("customers", customers);
        model.addAttribute("message", message);
        return "employee/manager/Users/manage_customer";
    }

    @GetMapping("/staff")
    public String viewStaffPage(Model model, @ModelAttribute("message") String message) {
        List<User> admins = userService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 2) // Assuming 2 is for admins
                .toList();
        List<User> managers = userService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 3) // Assuming 3 is for managers
                .toList();
        List<User> salesStaff = userService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 4) // Assuming 4 is for sales staff
                .toList();
        List<User> productionStaff = userService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 5) // Assuming 5 is for production staff
                .toList();
        List<User> designStaff = userService.getAllUsers().stream()
                .filter(user -> user.getRole_Id() == 6) // Assuming 6 is for design staff
                .toList();
        model.addAttribute("admins", admins);
        model.addAttribute("managers", managers);
        model.addAttribute("salesStaff", salesStaff);
        model.addAttribute("productionStaff", productionStaff);
        model.addAttribute("designStaff", designStaff);
        model.addAttribute("newStaff", new User()); // For adding new staff
        model.addAttribute("message", message);
        return "employee/manager/Users/manage_staff";
    }

    @GetMapping("/add_staff")
    public String addStaffPage(Model model) {
        model.addAttribute("newStaff", new User());
        return "employee/manager/Users/add_staff";
    }

    @PostMapping("/users/addStaff")
    public String addStaff(@ModelAttribute("newStaff") @Valid User newStaff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "employee/manager/Users/add_staff";
        }
        userService.saveUsers(newStaff);
        return "redirect:/staff";
    }

    @PostMapping("/users/activateCustomer/{id}")
    public String activateCustomer(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUsersById(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("message", "User not found.");
            } else {
                user.setStatus(true); // Set status to active
                userService.saveUsers(user);
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
            User user = userService.getUsersById(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("message", "User not found.");
            } else {
                user.setStatus(false); // Set status to inactive
                userService.saveUsers(user);
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
            User user = userService.getUsersById(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("message", "User not found.");
            } else {
                user.setStatus(true); // Set status to active
                userService.saveUsers(user);
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
            User user = userService.getUsersById(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("message", "User not found.");
            } else {
                user.setStatus(false); // Set status to inactive
                userService.saveUsers(user);
                redirectAttributes.addFlashAttribute("message", "Staff deactivated successfully.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error during deactivation: " + e.getMessage());
        }
        return "redirect:/staff";
    }

    @GetMapping("/manageUsers")
    public String manageUsersPage(Model model) {
        return "employee/manager/Users/manage_users";
    }

    @GetMapping("/users/showFormForUpdateCustomer/{id}")
    public String showFormForUpdateCustomer(@PathVariable("id") int id, Model model) {
        User user = userService.getUsersById(id);
        model.addAttribute("user", user);
        return "employee/manager/Users/update_customer";
    }

    @PostMapping("/users/updateCustomer/{id}")
    public String updateCustomer(@PathVariable("id") int id, @ModelAttribute("user") @Valid User user,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "employee/manager/Users/update_customer";
        }
        User existingUser = userService.getUsersById(id);
        if (existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword()); // Update the password
            userService.saveUsers(existingUser);
            redirectAttributes.addFlashAttribute("message", "Customer updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "User not found.");
        }
        return "redirect:/customers";
    }

    @GetMapping("/users/showFormForUpdateStaff/{id}")
    public String showFormForUpdateStaff(@PathVariable("id") int id, Model model) {
        User user = userService.getUsersById(id);
        model.addAttribute("user", user);
        return "employee/manager/Users/update_staff";
    }

    @PostMapping("/users/updateStaff/{id}")
    public String updateStaff(@PathVariable("id") int id, @ModelAttribute("user") @Valid User user,
            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "employee/manager/Users/update_staff";
        }
        User existingUser = userService.getUsersById(id);
        if (existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword()); // Update the password
            existingUser.setRole_Id(user.getRole_Id());
            userService.saveUsers(existingUser);
            redirectAttributes.addFlashAttribute("message", "Staff updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("message", "User not found.");
        }
        return "redirect:/staff";
    }

}
