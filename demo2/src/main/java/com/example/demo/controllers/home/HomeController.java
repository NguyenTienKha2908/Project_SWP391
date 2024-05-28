package com.example.demo.controllers.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/customer")
	public String customerHome() {
		return "customer/index";
	}

	@GetMapping("/staff")
	public String staffHome() {
		return "staff/index";
	}
}
