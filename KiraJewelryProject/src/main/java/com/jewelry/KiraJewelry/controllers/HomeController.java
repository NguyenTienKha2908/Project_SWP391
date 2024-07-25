package com.jewelry.KiraJewelry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jewelry.KiraJewelry.models.Employee;
import com.jewelry.KiraJewelry.models.User;
import com.jewelry.KiraJewelry.service.CustomerService;
import com.jewelry.KiraJewelry.service.EmployeeService;
import com.jewelry.KiraJewelry.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("requestURI", request.getRequestURI());
        return "home";
    }

    @GetMapping("/homeCustomer")
    public String homeCustomer() {
        return "customer/Profile";
    }

    @GetMapping("/homeManager")
    public String homeManager() {
        return "employee/manager/profile";
    }

    @GetMapping("/homeSalesStaff")
    public String homeSalesStaff() {
        return "employee/sales_staff/profile";
    }

    @GetMapping("/homeProductionStaff")
    public String homeProductionStaff() {
        return "employee/production_staff/profile";
    }

    @GetMapping("/homeDesignStaff")
    public String homeDesignStaff() {
        return "employee/design_staff/profile";
    }

    @GetMapping("/renameEmployee")
    public String rename(@RequestParam("employee_Id") String employee_Id, Model model) {
        Employee employee = employeeService.getEmployeeById(employee_Id);
        model.addAttribute("employee", employee);
        return "employee/rename";
    }

    @PostMapping("/renameEmployee")
    public String rename(@RequestParam("employee_Id") String employee_Id,
                         @RequestParam("newName") String newName,
            Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        Employee employee = employeeService.getEmployeeById(employee_Id);
        employee.setFull_Name(newName);
        employeeService.saveEmployee(employee);
        httpSession.setAttribute("employeeName", newName);
        User user = employee.getUser();
        redirectAttributes.addFlashAttribute("message", "Name successfully changed.");
        if (user.getRole_Id() == 3)
            return "redirect:/homeManager";
        if (user.getRole_Id() == 4)
            return "redirect:/homeSalesStaff";
        if (user.getRole_Id() == 5) {
            return "redirect:/homeDesignStaff";
        } else
            return "redirect:/homeProductionStaff";
    }

    @GetMapping("employee/editProfile")
    public String changePassword(@RequestParam("employee_Id") String employee_Id, Model model) {
        Employee employee = employeeService.getEmployeeById(employee_Id);
        model.addAttribute("employee", employee);

        return "employee/editProfile";
    }

    @PostMapping("/employee/editProfile")
    public String changePassword(@RequestParam("employee_Id") String employee_Id,
            @RequestParam("OldPassword") String OldPassword,
            @RequestParam("NewPassword") String NewPassword,
            @RequestParam("NewPasswordConfirm") String NewPasswordConfirm,
            Model model, RedirectAttributes redirectAttributes) {
        Employee employee = employeeService.getEmployeeById(employee_Id);
        User user = employee.getUser();
        if (!user.getPassword().equals(OldPassword)) {
            redirectAttributes.addFlashAttribute("error", "The old password is incorrect.");
            return "redirect:/employee/editProfile?employee_Id=" + employee_Id;
        }

        if (!NewPassword.equals(NewPasswordConfirm)) {
            redirectAttributes.addFlashAttribute("error", "The new password and confirmation do not match.");
            return "redirect:/employee/editProfile?employee_Id=" + employee_Id;

        }
        user.setPassword(NewPassword);
        userService.saveUsers(user);

        redirectAttributes.addFlashAttribute("message", "Password successfully changed.");

        if (user.getRole_Id() == 3)
            return "redirect:/homeManager";
        if (user.getRole_Id() == 4)
            return "redirect:/homeSalesStaff";
        if (user.getRole_Id() == 5) {
            return "redirect:/homeDesignStaff";
        } else
            return "redirect:/homeProductionStaff";
    }
    @GetMapping("/homePages")
    public String home(HttpSession session, Model model) {
        Employee employee = employeeService.getEmployeeById((String) session.getAttribute("employeeId"));
        User user = employee.getUser();
        if (user.getRole_Id() == 3)
            return "redirect:/homeManager";
        if (user.getRole_Id() == 4)
            return "redirect:/homeSalesStaff";
        if (user.getRole_Id() == 5) {
            return "redirect:/homeDesignStaff";
        } else
            return "redirect:/homeProductionStaff";
    }
}