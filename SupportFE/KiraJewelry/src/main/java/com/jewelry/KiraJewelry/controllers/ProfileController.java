package com.jewelry.KiraJewelry.controllers;

import com.jewelry.KiraJewelry.models.Customer;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(@RequestParam("CustomerId") String CustomerId, Model model) {
        Customer customer = customerService.getCustomerByCustomerId(CustomerId);
        User user = customer.getUser();
        model.addAttribute("user", user);
        model.addAttribute("customer",customer);
        System.out.println("Customer: " + customer.getCustomer_Id());
        return "customer/profile/profile";
    }
    @GetMapping("/profile/edit")
    public String editProfile(@RequestParam("CustomerId") String CustomerId, Model model) {
        Customer customer = customerService.getCustomerByCustomerId(CustomerId);
        model.addAttribute("customer",customer);
        return "customer/profile/editProfile";
    }
    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam("CustomerId") String CustomerId, @RequestParam("full_Name") String Full_Name, @RequestParam("address") String Address, @RequestParam("phoneNumber") String PhoneNumber, Model model) {
        Customer customer = customerService.getCustomerByCustomerId(CustomerId);
        customer.setFull_Name(Full_Name);
        customer.setAddress(Address);
        customer.setPhoneNumber(PhoneNumber);
        customerService.saveCustomer(customer);
        model.addAttribute("message", "Successfully updated!");
        return "redirect:/profile?CustomerId=" + CustomerId + "&success";
    }
    @GetMapping("/profile/changePassword")
    public String changePassword(@RequestParam("CustomerId") String CustomerId, Model model) {
        Customer customer = customerService.getCustomerByCustomerId(CustomerId);
        model.addAttribute("customer",customer);
        return "customer/profile/changePassword";
    }

    @PostMapping("/profile/changePassword")
    public String changePassword(@RequestParam("CustomerId") String CustomerId,
                                 @RequestParam("OldPassword") String OldPassword,
                                 @RequestParam("NewPassword") String NewPassword,
                                 @RequestParam("NewPasswordConfirm") String NewPasswordConfirm,
                                 Model model, RedirectAttributes redirectAttributes) {
        Customer customer = customerService.getCustomerByCustomerId(CustomerId);
        User user = customer.getUser();

        // Check if the old password matches the current password
        if (!user.getPassword().equals(OldPassword)) {
            redirectAttributes.addFlashAttribute("error", "The old password is incorrect.");
            return "redirect:/profile/changePassword?CustomerId=" + CustomerId + "&error";
        }

        // Check if the new password and confirmation match
        if (!NewPassword.equals(NewPasswordConfirm)) {
            redirectAttributes.addFlashAttribute("error", "The new password and confirmation do not match.");
            return "redirect:/profile/changePassword?CustomerId=" + CustomerId + "&error";
        }

        // If both checks pass, update the password
        user.setPassword(NewPassword);
        userService.saveUsers(user);

        // Redirect to the profile page with a success message
        redirectAttributes.addFlashAttribute("message", "Password successfully changed.");
        return "redirect:/profile?CustomerId=" + CustomerId + "&success";
    }

}
