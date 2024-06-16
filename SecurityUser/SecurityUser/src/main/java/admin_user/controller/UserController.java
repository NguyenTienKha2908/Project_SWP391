package admin_user.controller;

import admin_user.Dto.RegisterDto;
import admin_user.model.User;
import admin_user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") RegisterDto registerDto) {
        return "register";
    }

//    @PostMapping("/registration")
//    public String saveUser(@ModelAttribute("user") RegisterDto registerDto, Model model) {
//        userService.saveUserAndCustomer(registerDto);
//        model.addAttribute("message", "Registered Successfully!");
//        return "register";
//    }

    @PostMapping("/registration")
    public String registerSave(@Valid @RequestBody @ModelAttribute("user") RegisterDto registerDto, Model model) {
        User user = userService.findByEmail(registerDto.getEmail());
        if (user != null) {
            model.addAttribute("userexist", user);
            return "register";
        }else{
        userService.saveUserAndCustomer(registerDto);
        model.addAttribute("message", "Registered Successfully!");
        return "register";

        }

    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/user-page")
    public String userPage(){
        return "user";
    }

    @GetMapping("/admin-page")
    public String adminPage() {
        return "admin";
    }
    @GetMapping("/sale-page")
    public String salePage() {
        return "sale";
    }
    @GetMapping("/product-page")
    public String productPage() {
        return "product";
    }
    @GetMapping("/design-page")
    public String designPage() {
        return "design";
    }
    @GetMapping("/manager-page")
    public String managerPage() {
        return "manager";
    }
    @GetMapping("/homePage")
    public String homerPage() {
        return "homePage";
    }





}